package com.portfolio.controller.member;

import com.portfolio.controller.validator.SignUpFormValidator;
import com.portfolio.domain.Member;
import com.portfolio.repository.MemberRepository;
import com.portfolio.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    private final SignUpFormValidator signUpFormValidator;
    private final MemberService memberService;

    @InitBinder("signUpForm")
    public void initBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(signUpFormValidator);
    }

    @GetMapping("/sign-up")
    public String createNewSignUpFrom(Model model){
        model.addAttribute(new SignUpForm());
        return "member/sign-up";
    }

    @PostMapping("/sign-up")
    private String createMember(@Valid SignUpForm signUpForm, Errors errors){
        if(errors.hasErrors()){
            return "member/sign-up";
        }
        Member member = memberService.processNewMember(signUpForm);
        memberService.login(member);

        return "redirect:/";
    }

    @GetMapping("/check-email-token")
    public String checkEmailToken(String email, String token, Model model){
        Member member = memberRepository.findByEmail(email);
        System.out.println(email);
        System.out.println(member.getEmail());
        System.out.println(token);
        System.out.println(member.getEmailToken());
        String view = "member/checked-email";
        if(member == null){
            System.out.println("이메일오류");
            model.addAttribute("error","error.email");
            return view;
        }
        if(!member.checkedToken(token)){
            System.out.println("토큰오류");
            model.addAttribute("error","error.token");
            return view;
        }

        memberService.successCheckedEmail(member);
        model.addAttribute("memberId",member.getMemberId());
        return view;
    }
}
