package com.michael.request;

import javax.validation.constraints.NotEmpty;

public class UserLoginRequest {

    @NotEmpty(message = "Field is required")
    private String email;
    @NotEmpty(message = "Field is required")
    private String password;
}
