package com.michael.repository;

import com.michael.model.Comment;
import com.michael.model.LikeComment;
import com.michael.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeCommentRepository extends JpaRepository<LikeComment, Long> {

    Optional<LikeComment> findLikeCommentByUserAndComment(User user, Comment comment);
}
