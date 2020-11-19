package com.portfolio.controller.board;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
public class CommentForm {

    @Length(max = 200)
    @NotBlank
    private String reply;

}
