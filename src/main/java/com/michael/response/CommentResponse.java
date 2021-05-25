package com.michael.response;

import com.michael.model.Comment;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
public class CommentResponse {
    private Comment comment;
    private String message;
    private List<Comment> comments;
}
