package com.judychen.springbootmall.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class Member {

    @NotNull
    Integer memberId;
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
    @NotNull
    Date createdDate;
    @NotNull
    Date lastModifiedDate;

}
