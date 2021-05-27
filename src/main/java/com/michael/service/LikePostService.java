package com.michael.service;

import com.michael.model.LikePost;
import com.michael.model.Post;
import com.michael.model.User;
import com.michael.repository.LikePostRepository;
import com.michael.service.contracts.ILikePostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikePostService implements ILikePostService {

    @Autowired
    LikePostRepository likePostRepository;

    @Override
    public boolean isUserLikePost(User user, Post post) {
        Optional<LikePost> optionalLikePost = likePostRepository.findLikePostByUserAndPost(user, post);

        return optionalLikePost.isPresent();
    }

    @Override
    public void likePost(User user, Post post) {
        LikePost likePost = new LikePost();
        likePost.setUser(user);
        likePost.setPost(post);

        likePostRepository.save(likePost);
    }

    @Override
    public void unLikePost(User user, Post post) {

        Optional<LikePost> optionalLikePost = likePostRepository.findLikePostByUserAndPost(user, post);
        if (optionalLikePost.isPresent()) {
            LikePost likePost = optionalLikePost.get();
            likePostRepository.delete(likePost);
        }
    }

    @Override
    public int getTotalPostLikes(Post post) {
        return likePostRepository.countLikePostsByPost(post);
    }
}
