package com.backend.api.controller;

import com.backend.api.dto.CommentRequest;
import com.backend.api.dto.PostRequest;
import com.backend.api.dto.PostAuthorResponse;
import com.backend.api.model.Post;
import com.backend.api.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    
    private final PostService postService;

    @PostMapping
    public ResponseEntity<Post> createPost(@Valid @RequestBody PostRequest request) {
        return ResponseEntity.ok(postService.createPost(request));
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Post>> getUserPosts(@PathVariable String userId) {
        return ResponseEntity.ok(postService.getUserPosts(userId));
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<Post> likePost(@PathVariable String postId) {
        return ResponseEntity.ok(postService.likePost(postId));
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<Post> addComment(
            @PathVariable String postId,
            @Valid @RequestBody CommentRequest request) {
        return ResponseEntity.ok(postService.addComment(postId, request));
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<Post> removeComment(
            @PathVariable String postId,
            @PathVariable String commentId) {
        return ResponseEntity.ok(postService.removeComment(postId, commentId));
    }

    @GetMapping("/{postId}/author")
    public ResponseEntity<PostAuthorResponse> getPostAuthor(@PathVariable String postId) {
        return ResponseEntity.ok(postService.getPostAuthor(postId));
    }
} 