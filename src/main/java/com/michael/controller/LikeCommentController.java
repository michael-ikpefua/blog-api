package com.michael.controller;

import com.michael.exceptions.CommentException;
import com.michael.exceptions.UserException;
import com.michael.model.Comment;
import com.michael.model.Post;
import com.michael.model.User;
import com.michael.response.MessageResponse;
import com.michael.service.contracts.ICommentService;
import com.michael.service.contracts.ILikeCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(path = "comments")
public class LikeCommentController extends BaseController {

    @Autowired
    ILikeCommentService likeCommentService;

    @Autowired
    ICommentService commentService;

    @PostMapping(path = "{commentId}/likes")
    public ResponseEntity<?> store(@PathVariable Long commentId, HttpSession session) {
        User user = authUser(session);
        if (user == null) throw new UserException("Login, to like a comment");

        Comment comment = commentService.getCommentById(commentId);
        if (comment == null) throw new CommentException("Comment Not Found");

        likeCommentService.likeComment(user, comment);
        MessageResponse messageResponse = new MessageResponse("Comment is successfully Liked by " + user.getFullName());

        return new ResponseEntity<>(messageResponse, HttpStatus.CREATED);

    }


}
