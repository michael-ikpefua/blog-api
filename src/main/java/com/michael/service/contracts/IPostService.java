package com.michael.service.contracts;

import com.michael.model.Post;
import com.michael.model.User;
import com.michael.request.PostRequest;

import java.util.List;

public interface IPostService {
    Post addPost(PostRequest request, User user);
    List<Post> getPosts();
    Post getPostById(Long id);
    Post updatePost(Post post, PostRequest request);

}
