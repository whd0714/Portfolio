package com.portfolio.controller.release;

import com.portfolio.domain.Release;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Data
@NoArgsConstructor
public class ReleaseForm {

    @NotBlank
    @Length(max = 50)
    private String title;

    @NotBlank
    private String brand;

    @NotBlank
    private String modelNo;

    private String releaseTime;

    public ReleaseForm(Release release) {
        this.title = release.getTitle();
        this.brand = release.getBrand();
        this.modelNo = release.getModelNo();
        this.releaseTime = release.getReleaseTime().format(DateTimeFormatter.ISO_DATE);
    }
}
