package com.michael.service.contracts;

import com.michael.model.FavoritePost;
import com.michael.model.Post;
import com.michael.model.User;

public interface IFavoritePostService {
    void addFavoritePost(User user, Post post);

    void unFavoritePost(User user, Post post);

    boolean isUserFavoritePost(User user, Post post);

}
