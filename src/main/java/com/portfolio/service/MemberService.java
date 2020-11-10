package com.portfolio.service;

import com.portfolio.controller.member.SignUpForm;
import com.portfolio.controller.member.UserMember;
import com.portfolio.controller.settings.NotificationForm;
import com.portfolio.controller.settings.PasswordForm;
import com.portfolio.controller.settings.SettingMemberForm;
import com.portfolio.domain.Member;
import com.portfolio.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;
    private final MemberRepository memberRepository;

    public Member processNewMember(SignUpForm signUpForm){
        Member newMember = createNewMember(signUpForm);
        sendCheckEmail(newMember);
        return newMember;
    }

    public Member createNewMember(SignUpForm signUpForm){
        signUpForm.setPassword(passwordEncoder.encode(signUpForm.getPassword()));
        Member member = Member.builder()
                .memberId(signUpForm.getMemberId())
                .password(signUpForm.getPassword())
                .email(signUpForm.getEmail())
                .build();
        member.generateToken();
        memberRepository.save(member);
        return member;
    }

    public void sendCheckEmail(Member member){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(member.getEmail());
        simpleMailMessage.setSubject("이메일 인증");
        simpleMailMessage.setText("/check-email-token/?token=" + member.getEmailToken() +"&email=" + member.getEmail());
        javaMailSender.send(simpleMailMessage);
    }

    public void login(Member member){
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                new UserMember(member),
                member.getPassword(),
                List.of(new SimpleGrantedAuthority("LORE_USER"))
        );
        SecurityContextHolder.getContext().setAuthentication(token);

    }

    public void successCheckedEmail(Member member) {
        member.successCheckedEmailSettings();
        login(member);
    }

    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        Member member = memberRepository.findByMemberId(memberId);
        if (member == null){
            throw new UsernameNotFoundException(memberId);
        }

        return new UserMember(member);
    }

    public void updateSettingsProfile(Member member, SettingMemberForm settingMemberForm) {
        member.setShoeSize(settingMemberForm.getShoeSize());
        System.out.println(member.getShoeSize());
        memberRepository.save(member);
    }

    public void updatePassword(Member member, PasswordForm passwordForm) {
        String encode = passwordEncoder.encode(passwordForm.getNewPassword());
        member.setPassword(encode);
        memberRepository.save(member);
    }

    public void updateNotification(Member member, NotificationForm notificationForm) {
        member.setWebRelease(notificationForm.isWebRelease());
        member.setEmailRelease(notificationForm.isEmailRelease());
        member.setWebLocale(notificationForm.isWebLocale());
        member.setEmailLocale(notificationForm.isEmailLocale());

        memberRepository.save(member);

    }
}
