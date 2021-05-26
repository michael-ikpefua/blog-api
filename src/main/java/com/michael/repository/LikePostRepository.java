package com.michael.repository;

import com.michael.model.LikePost;
import com.michael.model.Post;
import com.michael.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikePostRepository extends JpaRepository<LikePost, Long> {

    Optional<LikePost> findLikePostByUserAndPost(User user, Post post);

}
