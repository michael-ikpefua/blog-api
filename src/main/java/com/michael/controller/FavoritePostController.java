package com.michael.controller;

import com.michael.exceptions.FavoritePostException;
import com.michael.exceptions.PostException;
import com.michael.exceptions.UserException;
import com.michael.model.Post;
import com.michael.model.User;
import com.michael.response.MessageResponse;
import com.michael.service.contracts.IFavoritePostService;
import com.michael.service.contracts.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(path = "favorites")
public class FavoritePostController extends BaseController {

    @Autowired
    IPostService postService;

    @Autowired
    IFavoritePostService favoritePostService;

    @GetMapping(path = "{postId}")
    public ResponseEntity<?> store(@PathVariable Long postId, HttpSession session) {
        User authUser = authUser(session);

        if (authUser == null) {
            throw new UserException("Please login, to favorite a Post..");
        }

        Post post = postService.getPostById(postId);
        if (post == null) {
            throw new PostException("Post Not Found!!!");
        }

        boolean isAlreadyFavorited = checkIfUserHasFavoritedPost(authUser, post);
        if (isAlreadyFavorited) {
            throw  new FavoritePostException("User Already Favorited this Post..");
        }

        favoritePostService.addFavoritePost(authUser, post);
        MessageResponse messageResponse = new MessageResponse("Post is Favorited!!!");
        return new ResponseEntity<>(messageResponse, new HttpHeaders(), HttpStatus.CREATED);

    }

    @DeleteMapping(path = "{postId}")
    public ResponseEntity<?> destroy(@PathVariable Long postId, HttpSession session) {
        User user = authUser(session);
        if (user == null) throw new UserException("Please login, to favorite a Post..");
        Post post = postService.getPostById(postId);
        if (post == null) throw new PostException("Post Not Found!!!");
        boolean isAlreadyFavorited = checkIfUserHasFavoritedPost(user, post);
        if (!isAlreadyFavorited) throw  new FavoritePostException("This post is not in your list of favorited Post..");

        favoritePostService.unFavoritePost(user, post);
        MessageResponse messageResponse = new MessageResponse("Post is removed from Favorite");
        return new ResponseEntity<>(messageResponse, new HttpHeaders(), HttpStatus.OK);
    }

    boolean checkIfUserHasFavoritedPost(User user, Post post) {
        return favoritePostService.isUserFavoritePost(user, post);
    }
}
