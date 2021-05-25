package com.michael.service;

import com.michael.model.Post;
import com.michael.model.User;
import com.michael.repository.PostRepository;
import com.michael.request.PostRequest;
import com.michael.service.contracts.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService implements IPostService {

    @Autowired
    PostRepository postRepository;

    @Override
    public Post addPost(PostRequest request, User user) {
        Post post = new Post();
        post.setUser(user);
        post.setTitle(request.getTitle());
        post.setBody(request.getBody());

        return postRepository.save(post);
    }
}
