package com.michael.service;

import com.michael.model.FavoritePost;
import com.michael.model.Post;
import com.michael.model.User;
import com.michael.repository.FavoritePostRepository;
import com.michael.repository.PostRepository;
import com.michael.service.contracts.IFavoritePostService;
import com.michael.service.contracts.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavoritePostService implements IFavoritePostService {

    @Autowired
    FavoritePostRepository favoritePostRepository;

    @Autowired
    PostRepository postRepository;

    @Override
    public List<Post> getAllUserFavoritePosts(User user) {
        List<FavoritePost> favoritePosts = favoritePostRepository.findFavoritePostByUser(user);

        if (favoritePosts != null) {
            List<Long> ids = favoritePosts.stream().map(post -> post.getPost().getId()).toList();
            List<Post> posts = postRepository.findByIdIn(ids);

            return posts;

        }

        return  null;
    }

    @Override
    public void addFavoritePost(User user, Post post) {

        FavoritePost favoritePost = new FavoritePost();
        favoritePost.setUser(user);
        favoritePost.setPost(post);

        favoritePostRepository.save(favoritePost);

    }

    @Override
    public void unFavoritePost(User user, Post post) {
        Optional<FavoritePost> optionalFavoritePost = favoritePostRepository.findFavoritePostByUserAndPost(user, post);

        if (optionalFavoritePost.isPresent()) {
            FavoritePost favoritePost = optionalFavoritePost.get();
            favoritePostRepository.delete(favoritePost);
        }
    }

    @Override
    public boolean isUserFavoritePost(User user, Post post) {
        Optional<FavoritePost> optionalFavoritePost = favoritePostRepository.findFavoritePostByUserAndPost(user, post);

        return  optionalFavoritePost.isPresent();
    }

}
