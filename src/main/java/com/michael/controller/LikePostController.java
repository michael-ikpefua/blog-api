package com.michael.controller;

import com.michael.exceptions.LikePostException;
import com.michael.exceptions.PostException;
import com.michael.exceptions.UserException;
import com.michael.model.Post;
import com.michael.model.User;
import com.michael.response.MessageResponse;
import com.michael.service.contracts.ILikePostService;
import com.michael.service.contracts.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(path = "posts")
public class LikePostController extends BaseController{

    @Autowired
    ILikePostService likePostService;

    @Autowired
    IPostService postService;


    @PostMapping(path = "{postId}/like")
    public ResponseEntity<?> store(@PathVariable Long postId, HttpSession session) {

        User authUser = authUser(session);

        if (authUser == null) {
            throw new UserException("Please login, before you can like post.");
        }

        Post post = getPost(postId);
        if (post == null) {
            throw new PostException("The post you tryna like doesn't exist!!!");
        }

        boolean isPostLiked = isPostLiked(authUser, post);

        if (isPostLiked) {
            throw new LikePostException(authUser.getFullName() + " already liked " + post.getTitle() + " post");
        }

        likePostService.likePost(authUser, post);
        MessageResponse messageResponse = new MessageResponse(post.getTitle() + " is Liked by " + authUser.getFullName());

        return new ResponseEntity<>(messageResponse, HttpStatus.CREATED);

    }

    @DeleteMapping("{postId}/dislike")
    public ResponseEntity<?> destroy(@PathVariable Long postId, HttpSession session) {

        User authUser = authUser(session);

        if (authUser == null) {
            throw new UserException("Please login, before you can like post.");
        }
        Post post = getPost(postId);
        if (post == null) {
            throw new PostException("The post you tryna like doesn't exist!!!");
        }
        boolean isPostLiked = isPostLiked(authUser, post);

        if (!isPostLiked) {
            throw new LikePostException(authUser.getFullName() + " you haven't liked " + post.getTitle() + " post");
        }

        likePostService.unLikePost(authUser, post);
        MessageResponse messageResponse = new MessageResponse(post.getTitle() + " post has been disliked by " + authUser.getFullName());

        return new ResponseEntity<>(messageResponse, HttpStatus.OK);

    }

    @GetMapping("{postId}/total-likes")
    public ResponseEntity<?> totalLikes(@PathVariable Long postId, HttpSession session) {
        Post post = postService.getPostById(postId);
        User user = authUser(session);
        if (user == null) throw new UserException("Please login to view total post likes");
        if (post == null) throw new PostException("Post not Found!!!");

        int totalPostLikes = likePostService.getTotalPostLikes(post);
        MessageResponse messageResponse = new MessageResponse("Total likes for " + post.getTitle() + " is " + totalPostLikes);

        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }

    private Post getPost(Long postId) {
        Post post = postService.getPostById(postId);

        return post;
    }

    private boolean isPostLiked(User user, Post post) {
        return likePostService.isUserLikePost(user, post);
    }

}
