package com.michael.controller;

import com.michael.exception.CommentException;
import com.michael.exception.PostException;
import com.michael.exception.UserException;
import com.michael.model.Comment;
import com.michael.model.Post;
import com.michael.model.User;
import com.michael.request.CommentRequest;
import com.michael.response.CommentResponse;
import com.michael.service.contracts.ICommentService;
import com.michael.service.contracts.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RestController

public class CommentController {

    @Autowired
    IPostService postService;

    @Autowired
    ICommentService commentService;
    @Autowired
    CommentResponse commentResponse;

    @GetMapping(path = "posts/{postId}/comments")
    @Transactional
    public ResponseEntity<CommentResponse> index(@PathVariable(value = "postId") Long postId) {
        Post post = postService.getPostById(postId);
        if (post == null) {
            throw new PostException("Post Not Found");
        }
        List<Comment> comments = post.getComments();
        commentResponse.setMessage("List of Comments for " + post.getTitle());
        commentResponse.setComments(comments);

        return new ResponseEntity<>(commentResponse, HttpStatus.OK);
    }

    @PostMapping(path = "posts/{postId}/comment")
    public ResponseEntity<CommentResponse> store(@Valid @RequestBody CommentRequest request, @PathVariable(value = "postId") Long postId, HttpSession session) {
        Post post = postService.getPostById(postId);
        if (post == null) {
            throw new PostException("Post Not Found");
        }
        User authUser = authUser(session);
        if (authUser == null) {
            throw new UserException("Please login, before you can add comment");
        }

        Comment comment = commentService.addComment(request, post, authUser);
        commentResponse.setComment(comment);
        commentResponse.setMessage("Comment Added Successfully!!!");

        return new ResponseEntity<>(commentResponse, HttpStatus.OK);
    }

    @PutMapping(path = "comment/{commentId}")
    public ResponseEntity<CommentResponse> update(@PathVariable(value = "commentId") Long commentId, @Valid @RequestBody CommentRequest request) {

        Comment comment = commentService.getCommentById(commentId);
        if (comment == null) {
            throw new CommentException("Comment Not Found");
        }

        Comment updatedComment = commentService.updateComment(comment, request);
        commentResponse.setComment(updatedComment);
        commentResponse.setMessage("Comment Updated Successfully!!!");

        return new ResponseEntity<>(commentResponse, HttpStatus.OK);

    }

    public User authUser(HttpSession session) {
        return (User) session.getAttribute("user_session");
    }
}
