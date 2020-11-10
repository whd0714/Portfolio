package com.portfolio.controller.settings;

import com.portfolio.controller.member.CurrentUser;
import com.portfolio.controller.validator.PasswordFormValidator;
import com.portfolio.domain.Member;
import com.portfolio.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class SettingsController {

    private final MemberService memberService;

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

}
