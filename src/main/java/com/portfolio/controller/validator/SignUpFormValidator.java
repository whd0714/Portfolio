package com.portfolio.controller.validator;

import com.portfolio.controller.member.SignUpForm;
import com.portfolio.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class SignUpFormValidator implements Validator {

    private final MemberRepository memberRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return SignUpForm.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        SignUpForm signUpForm = (SignUpForm) o;

        if(memberRepository.existsByMemberId(signUpForm.getMemberId())){
            errors.rejectValue("memberId","invalid.memberId",null,"이미 존재하는 아이디입니다.");
        }
        if(memberRepository.existsByEmail(signUpForm.getEmail())){
            errors.rejectValue("email","invalid.email",null,"이미 사용하고 있는 이메일입니다.");
        }
        if(!signUpForm.getPassword().equals(signUpForm.getPasswordConfirm())){
            errors.rejectValue("password","invalid.password",null,"비밀번호가 일치하지 않습니다.");
        }

    }
}
