package com.michael.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UserRegisterRequest {
    @NotEmpty(message = "Field cannot be blank")
    private String fullName;
    @NotEmpty(message = "Email field cannot be empty")
    @Email
    private String email;
    @NotEmpty(message = "Password field is required")
    @Min(value = 4, message = "Password filed should be more than 4 character long")
    private String password;
}
