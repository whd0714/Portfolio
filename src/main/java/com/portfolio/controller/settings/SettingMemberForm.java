package com.portfolio.controller.settings;

import com.portfolio.domain.Member;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class SettingMemberForm {

    private String memberId;

    private String Email;

    private int shoeSize;

    public SettingMemberForm(Member member) {
        this.memberId = member.getMemberId();
        this.Email = member.getEmail();
        this.shoeSize = member.getShoeSize();
    }
}
