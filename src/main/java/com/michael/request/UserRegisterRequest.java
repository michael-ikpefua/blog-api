package com.michael.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@Component
public class UserRegisterRequest {
    @NotEmpty(message = "Field cannot be blank")
    private String fullName;
    @NotEmpty(message = "Email field cannot be empty")
    @Email
    private String email;
    @NotEmpty(message = "Password field is required")
    @Size(min = 3, message = "Password field should be more than 3 characters long")
    private String password;
}
