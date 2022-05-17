package com.judychen.springbootmall.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class MemberRequest {

    @NotBlank
    String accountNumber;
    @NotBlank
    String accountPassword;
    @NotBlank
    String memberFirstname;
    @NotBlank
    String memberLastname;
    @NotBlank
    String contactPhone;
    String photoUrl;
}
