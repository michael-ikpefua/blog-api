package com.michael.controller;

import com.michael.exceptions.CommentException;
import com.michael.exceptions.PostException;
import com.michael.exceptions.UserException;
import com.michael.model.Comment;
import com.michael.model.Post;
import com.michael.model.User;
import com.michael.request.CommentRequest;
import com.michael.response.CommentResponse;
import com.michael.response.PostResponse;
import com.michael.service.contracts.ICommentService;
import com.michael.service.contracts.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController

public class CommentController {

    @Autowired
    IPostService postService;

    @Autowired
    ICommentService commentService;

    @Autowired
    PostResponse postResponse;

    @GetMapping(path = "posts/{postId}/comments")
    @Transactional
    public ResponseEntity<CommentResponse> index(@PathVariable(value = "postId") Long postId) {
        Post post = postService.getPostById(postId);
        if (post == null) {
            throw new PostException("Post Not Found");
        }
        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setMessage("List of Comments for " + post.getTitle());
        commentResponse.setComments(post.getComments());

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
        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setComment(comment);
        commentResponse.setMessage("Comment Added Successfully!!!");

        return new ResponseEntity<>(commentResponse, HttpStatus.OK);
    }

    @GetMapping("/comments/{commentId}")
    public ResponseEntity<?> show(@PathVariable Long commentId){
        Comment comment = commentService.getCommentById(commentId);
        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setComment(comment);
        commentResponse.setMessage("Comment Details");
        //In Comment Response , add user object

        return new ResponseEntity<>(commentResponse, new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping(path = "comments/{commentId}")
    public ResponseEntity<CommentResponse> update(@PathVariable(value = "commentId") Long commentId, @Valid @RequestBody CommentRequest request) {

        Comment comment = commentService.getCommentById(commentId);
        if (comment == null) {
            throw new CommentException("Comment Not Found");
        }

        Comment updatedComment = commentService.updateComment(comment, request);
        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setComment(updatedComment);
        commentResponse.setMessage("Comment Updated Successfully!!!");

        return new ResponseEntity<>(commentResponse, HttpStatus.OK);

    }

    @DeleteMapping(path = "comments/{commentId}")
    public ResponseEntity<?> destroy(@PathVariable(value = "commentId") Long commentId) {
        commentService.destroyComment(commentId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public User authUser(HttpSession session) {
        return (User) session.getAttribute("user_session");
    }
}
