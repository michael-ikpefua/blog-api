package com.michael.service.contracts;

import com.michael.model.Comment;
import com.michael.model.User;

public interface ILikeCommentService {

    void likeComment(User user, Comment comment);
}
