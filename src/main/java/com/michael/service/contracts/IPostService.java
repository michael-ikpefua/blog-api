package com.michael.service.contracts;

import com.michael.model.Post;
import com.michael.model.User;
import com.michael.request.PostRequest;

public interface IPostService {
    Post addPost(PostRequest request, User user);

}
