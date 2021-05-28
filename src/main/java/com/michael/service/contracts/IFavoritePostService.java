package com.michael.service.contracts;

import com.michael.model.FavoritePost;
import com.michael.model.Post;
import com.michael.model.User;

import java.util.List;

public interface IFavoritePostService {

    List<Post> getAllUserFavoritePosts(User user);

    void addFavoritePost(User user, Post post);

    void unFavoritePost(User user, Post post);

    boolean isUserFavoritePost(User user, Post post);

}
