package com.portfolio.controller.settings;

import com.portfolio.domain.Member;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NotificationForm {

    private boolean webRelease;

    private boolean emailRelease;

    private boolean webLocale;

    private boolean emailLocale;

    public NotificationForm(Member member) {
        this.webRelease = member.isWebRelease();
        this.emailRelease = member.isEmailRelease();
        this.webLocale =member.isWebLocale();
        this.emailLocale = member.isEmailLocale();
    }
}
