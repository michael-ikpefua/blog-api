package com.michael.controller;

import com.michael.exception.LikePostException;
import com.michael.exception.PostException;
import com.michael.exception.UserException;
import com.michael.model.Post;
import com.michael.model.User;
import com.michael.response.LikePostResponse;
import com.michael.service.contracts.ILikePostService;
import com.michael.service.contracts.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(path = "posts")
public class LikePostController {

    @Autowired
    ILikePostService likePostService;

    @Autowired
    IPostService postService;

    @Autowired
    LikePostResponse likePostResponse;

    @PostMapping(path = "{postId}/like")
    public ResponseEntity<?> store(@PathVariable Long postId, HttpSession session) {

        User authUser = authUser(session);

        Post post = getPost(postId);

        boolean isPostLiked = isPostLiked(authUser, post);

        if (isPostLiked) {
            throw new LikePostException(authUser.getFullName() + " already liked " + post.getTitle() + " post");
        }

        likePostService.likePost(authUser, post);

        likePostResponse.setMessage(post.getTitle() + " is Liked by " + authUser.getFullName());

        return new ResponseEntity<>(likePostResponse, HttpStatus.OK);

    }

    @DeleteMapping("{postId}/dislike")
    public ResponseEntity<?> destroy(@PathVariable Long postId, HttpSession session) {

        User authUser = authUser(session);

        Post post = getPost(postId);

        boolean isPostLiked = isPostLiked(authUser, post);

        if (isPostLiked) {
            throw new LikePostException(authUser.getFullName() + " already liked " + post.getTitle() + " post");
        }

        likePostService.unLikePost(authUser, post);
        likePostResponse.setMessage(post.getTitle() + " post has been disliked by " + authUser.getFullName());

        return new ResponseEntity<>(likePostResponse, HttpStatus.OK);

    }

    public User authUser(HttpSession session) {
        User authUser = (User) session.getAttribute("user_session");

        if (authUser == null) {
            throw new UserException("Please login, before you can like post.");
        }

        return authUser;
    }

    private Post getPost(Long postId) {
        Post post = postService.getPostById(postId);

        if (post == null) {
            throw new PostException("The post you tryna like doesn't exist!!!");
        }

        return post;
    }

    private boolean isPostLiked(User user, Post post) {
        return likePostService.isUserLikePost(user, post);
    }

}
