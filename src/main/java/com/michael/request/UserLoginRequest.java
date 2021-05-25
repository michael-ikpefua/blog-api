package com.michael.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Component
public class UserLoginRequest {

    @NotEmpty(message = "Field is required")
    private String email;
    @NotEmpty(message = "Field is required")
    private String password;
}
