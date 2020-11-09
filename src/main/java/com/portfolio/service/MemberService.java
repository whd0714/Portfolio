package com.portfolio.service;

import com.portfolio.controller.member.SignUpForm;
import com.portfolio.domain.Member;
import com.portfolio.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

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
                member.getMemberId(),
                member.getPassword(),
                List.of(new SimpleGrantedAuthority("LORE_USER"))
        );
        SecurityContextHolder.getContext().setAuthentication(token);

    }

    public void successCheckedEmail(Member member) {
        member.successCheckedEmailSettings();
    }
}
