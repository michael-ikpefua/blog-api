package com.michael.controller;

import com.michael.exception.UserException;
import com.michael.model.Post;
import com.michael.model.User;
import com.michael.request.PostRequest;
import com.michael.response.PostResponse;
import com.michael.service.contracts.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "posts")
public class PostController {

    @Autowired
    IPostService postService;

    @GetMapping()

    @PostMapping(value = "create")
    public ResponseEntity<PostResponse> store(@Valid @RequestBody PostRequest request, HttpSession session) {

        User authUser = (User) session.getAttribute("user_session");
        if (authUser == null) throw new UserException("Please login before you can create post");
        Post post = postService.addPost(request, authUser);

        return new ResponseEntity<>(new PostResponse(post, "Post Added Successfully!"), HttpStatus.OK);
    }
}
