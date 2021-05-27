package com.michael.service;

import com.michael.model.Comment;
import com.michael.model.LikeComment;
import com.michael.model.User;
import com.michael.repository.LikeCommentRepository;
import com.michael.service.contracts.ILikeCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeCommentService implements ILikeCommentService {

    @Autowired
    LikeCommentRepository likeCommentRepository;

    @Override
    public void likeComment(User user, Comment comment) {
        LikeComment likeComment = new LikeComment();
        likeComment.setUser(user);
        likeComment.setComment(comment);

        likeCommentRepository.save(likeComment);

    }

    @Override
    public void disLikeComment(User user, Comment comment) {
        Optional<LikeComment> optionalLikeComment = likeCommentRepository.findLikeCommentByUserAndComment(user, comment);
        if (optionalLikeComment.isPresent()) {
            LikeComment likeComment = optionalLikeComment.get();
            likeCommentRepository.delete(likeComment);
        }
    }

    @Override
    public boolean isUserLikedComment(User user, Comment comment) {
        Optional<LikeComment> optionalLikeComment = likeCommentRepository.findLikeCommentByUserAndComment(user, comment);

        return optionalLikeComment.isPresent();
    }
}
