package com.michael.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CommentRequest {
    @NotEmpty(message = "Body field cannot be empty")
    @Size(min = 3, message = "Mimimum characters for body is 3")
    private String body;

}
