package com.portfolio.controller.validator;

import com.portfolio.controller.settings.PasswordForm;
import com.portfolio.controller.settings.SettingMemberForm;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PasswordFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isAssignableFrom(PasswordForm.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        PasswordForm passwordForm = (PasswordForm) o;
        if(!passwordForm.getNewPassword().equals(passwordForm.getNewPasswordConfirm())){
            errors.rejectValue("newPassword","error.newPassword","비밀번호가 일치하지 않습니다.");
        }
    }
}
