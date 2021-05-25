package com.michael.controller;

import com.michael.exception.PostException;
import com.michael.exception.UserException;
import com.michael.model.Post;
import com.michael.model.User;
import com.michael.request.PostRequest;
import com.michael.response.PostResponse;
import com.michael.service.contracts.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "posts")
public class PostController {

    @Autowired
    IPostService postService;

    @GetMapping(path = "")
    public ResponseEntity<?> index() {
        List<Post> posts = postService.getPosts();
        PostResponse postResponse = new PostResponse();
        postResponse.setPosts(posts);
        postResponse.setMessage("List of Posts");
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @PostMapping(value = "create")
    public ResponseEntity<PostResponse> store(@Valid @RequestBody PostRequest request, HttpSession session) {

        User authUser = authUser(session);
        if (authUser == null) throw new UserException("Please login before you can create post");
        Post post = postService.addPost(request, authUser);

        PostResponse postResponse = new PostResponse();
        postResponse.setPost(post);
        postResponse.setMessage("Post Added Successfully!");

        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<PostResponse> update(@PathVariable(value = "id") Long id, @Valid @RequestBody PostRequest request, HttpSession session) {
        Post post = postService.getPostById(id);
        if (post == null) {
            throw new PostException("Post Not Found!");
        }
        User authUser = authUser(session);
        if (authUser == null) {
            throw new UserException("Please Login to Update Post");
        }
        if (post.getUser().getId() != authUser.getId()) {
            throw new UserException("User is not the owner of Post.");
        }

        Post updatedPost = postService.updatePost(post, request);
        PostResponse postResponse = new PostResponse();
        postResponse.setPost(updatedPost);
        postResponse.setMessage("Post Updated Successfully!");
        return new ResponseEntity<>(postResponse, HttpStatus.OK);

    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<?> destroy(@PathVariable(value = "id") Long id, HttpSession session) {

        User authUser = authUser(session);
        Post post = postService.getPostById(id);

        if (authUser == null) {
            throw new UserException("Please Login before you can delete Post");
        }

        if (post == null) {
            throw new PostException("Post Not Found");
        }

        if (authUser.getId() != post.getUser().getId()) {
            throw new UserException("User cannot delete this post, because this user is not the owner of the post");
        }

        postService.destroyPost(id);
        PostResponse postResponse = new PostResponse();
        postResponse.setMessage("Post Deleted Successfully!");

        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    public User authUser(HttpSession session) {
        return (User) session.getAttribute("user_session");
    }


}
