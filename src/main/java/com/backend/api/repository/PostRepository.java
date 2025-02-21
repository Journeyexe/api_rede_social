package com.backend.api.repository;

import com.backend.api.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends MongoRepository<Post, String> {
    List<Post> findAllByOrderByCreatedAtDesc();
    List<Post> findByUserIdOrderByCreatedAtDesc(String userId);
    
    @Query(value = "{ '_id': ?0 }", fields = "{ 'likes': 1 }")
    Optional<Post> findLikesById(String postId);
} 