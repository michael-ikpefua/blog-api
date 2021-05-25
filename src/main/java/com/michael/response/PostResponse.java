package com.michael.response;

import com.michael.model.Post;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
public class PostResponse {
    private Post post;
    private String message;
    private List<Post> posts;
}
