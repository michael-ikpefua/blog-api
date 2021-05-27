package com.michael.repository;

import com.michael.model.LikeComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeCommentRepository extends JpaRepository<LikeComment, Long> {
}
