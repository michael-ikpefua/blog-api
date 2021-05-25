package com.michael.response;

import com.michael.model.Post;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostResponse {
    private Post post;
    private String message;
    private List<Post> posts;
}
