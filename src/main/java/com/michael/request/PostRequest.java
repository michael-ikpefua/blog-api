package com.michael.request;

import com.michael.model.User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class PostRequest {
    private User user;
    @NotEmpty(message = "Title field cannot be empty")
    private String title;
    @NotEmpty(message = "Body field cannot be empty")
    private String body;
}
