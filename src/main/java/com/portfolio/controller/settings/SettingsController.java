package com.portfolio.controller.settings;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.controller.member.CurrentUser;
import com.portfolio.controller.validator.PasswordFormValidator;
import com.portfolio.domain.Keyword;
import com.portfolio.domain.Member;
import com.portfolio.repository.KeywordRepository;
import com.portfolio.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class SettingsController {

    private final MemberService memberService;
    private final KeywordRepository keywordRepository;
    private final ObjectMapper objectMapper;

    @InitBinder("passwordForm")
    public void SettingMemberFormBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(new PasswordFormValidator());
    }

    @GetMapping("/profile")
    public String viewProfileForm(@CurrentUser Member member, Model model){
        model.addAttribute(member);
        model.addAttribute(new SettingMemberForm(member));

        return "settings/profile";
    }

    @PostMapping("/profile")
    public String viewProfile(@CurrentUser Member member, @Valid SettingMemberForm settingMemberForm, Errors errors,
                              Model model, RedirectAttributes attributes){
        if(errors.hasErrors()){
            model.addAttribute(member);
            return "settings/profile";
        }

        attributes.addFlashAttribute("message","회원정보가 수정되었습니다.");
        memberService.updateSettingsProfile(member, settingMemberForm);

        return "redirect:/profile";
    }

    @GetMapping("/settings/password")
    public String updatePasswordForm(@CurrentUser Member member, Model model){
        model.addAttribute(member);
        model.addAttribute(new PasswordForm());

        return "settings/password";
    }

    @PostMapping("/settings/password")
    public String updatePassword(@CurrentUser Member member, @Valid PasswordForm passwordForm, Errors errors,
                                 Model model, RedirectAttributes attributes){
        if(errors.hasErrors()){
            model.addAttribute(member);
            return "settings/password";
        }

        attributes.addFlashAttribute("message","비밀번호가 변경되었습니다.");
        memberService.updatePassword(member, passwordForm);
        return "redirect:/settings/password";
    }

    @GetMapping("/settings/notification")
    public String updateNotificationForm(@CurrentUser Member member, Model model){
        model.addAttribute(member);
        model.addAttribute(new NotificationForm(member));

        return "settings/notification";
    }

    @PostMapping("/settings/notification")
    public String updateNotification(@CurrentUser Member member, @Valid NotificationForm notificationForm, Errors errors,
                                     Model model, RedirectAttributes attributes){
        if(errors.hasErrors()){
            model.addAttribute(member);
            return "settings/notification";
        }

        attributes.addFlashAttribute("message", "알림 설정이 되었습니다");
        memberService.updateNotification(member, notificationForm);
        return "redirect:/settings/notification";
    }

    @GetMapping("/settings/keyword")
    public String updateKeywordForm(@CurrentUser Member member, Model model){
        model.addAttribute(member);
        List<Keyword> keyword = memberService.getKeyword(member);

        model.addAttribute("keywordList",keyword.stream().map(Keyword::getTitle).collect(Collectors.toList()));

        List<String> allKeyword = keywordRepository.findAll().stream().map(Keyword::getTitle).collect(Collectors.toList());
        model.addAttribute("whitelist", allKeyword);

        return "settings/keyword";
    }

    @PostMapping("/settings/keyword/add")
    @ResponseBody
    public ResponseEntity addKeyword(@CurrentUser Member member, @RequestBody KeywordForm keywordForm) {
        String title = keywordForm.getTitle();
        Keyword keyword = keywordRepository.findByTitle(title);

        if(keyword == null){
            keyword = keywordRepository.save(Keyword.builder().title(keywordForm.getTitle()).build());
        }
        memberService.addKeyword(member, keyword);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/settings/keyword/remove")
    @ResponseBody
    public ResponseEntity removeKeyword(@CurrentUser Member member, @RequestBody KeywordForm keywordForm){
        String title = keywordForm.getTitle();
        Keyword keyword = keywordRepository.findByTitle(title);

        if(keyword == null){
            keyword = keywordRepository.save(Keyword.builder().title(keywordForm.getTitle()).build());
        }

        memberService.removeKeyword(member, keyword);
        return ResponseEntity.ok().build();
    }

}
