package com.michael.service.contracts;

import com.michael.model.Post;
import com.michael.model.User;

public interface ILikePostService {

    boolean isUserLikePost(User user, Post post);

    void likePost(User user, Post post);

    void unLikePost(User user, Post post);

    int getTotalPostLikes(Post post);
}
