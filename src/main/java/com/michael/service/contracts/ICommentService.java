package com.michael.service.contracts;

import com.michael.model.Comment;
import com.michael.model.Post;
import com.michael.model.User;
import com.michael.request.CommentRequest;

import java.util.List;
import java.util.Set;

public interface ICommentService {

    Comment addComment(CommentRequest request, Post post, User user);

    Comment getCommentById(Long id);

    Comment updateComment(Comment comment, CommentRequest request);

    void destroyComment(Long commentId);
}
