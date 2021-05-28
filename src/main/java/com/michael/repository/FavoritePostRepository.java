package com.michael.repository;

import com.michael.model.FavoritePost;
import com.michael.model.Post;
import com.michael.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoritePostRepository extends JpaRepository<FavoritePost, Long> {

    Optional<FavoritePost> findFavoritePostByUserAndPost(User user, Post post);

    List<FavoritePost> findFavoritePostByUser(User user);
}
