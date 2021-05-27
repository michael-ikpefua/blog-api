package com.michael.service;

import com.michael.model.Comment;
import com.michael.model.Post;
import com.michael.model.User;
import com.michael.repository.CommentRepository;
import com.michael.request.CommentRequest;
import com.michael.service.contracts.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CommentService implements ICommentService {

    @Autowired
    CommentRepository commentRepository;

    @Override
    public Comment addComment(CommentRequest request, Post post, User user) {
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setPost(post);
        System.err.println(user.getFullName() + " " + post.getTitle());
        comment.setBody(request.getBody());

        return commentRepository.save(comment);
    }

    @Override
    public Comment getCommentById(Long id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);

        return optionalComment.orElse(null);
    }

    @Override
    public Comment updateComment(Comment comment, CommentRequest request) {
        comment.setBody(request.getBody());

        return commentRepository.save(comment);
    }

    @Override
    public void destroyComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
