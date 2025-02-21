package com.backend.api.service;

import com.backend.api.dto.PostRequest;
import com.backend.api.dto.CommentRequest;
import com.backend.api.model.Post;
import com.backend.api.model.User;
import com.backend.api.model.Comment;
import com.backend.api.repository.PostRepository;
import com.backend.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.backend.api.exception.ResourceNotFoundException;
import com.backend.api.exception.UnauthorizedException;
import com.backend.api.dto.PostAuthorResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Post createPost(PostRequest request) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        var post = Post.builder()
                .userId(currentUser.getId())
                .username(currentUser.getUsername())
                .userProfilePicture(currentUser.getProfileImage())
                .title(request.getTitle())
                .description(request.getDescription())
                .imageUrl(request.getImageUrl())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAllByOrderByCreatedAtDesc();
    }

    public List<Post> getUserPosts(String userId) {
        return postRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public Post likePost(String postId) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post não encontrado"));

        if (post.getLikes().contains(currentUser.getId())) {
            // Remove o like
            post.getLikes().remove(currentUser.getId());
            post.setLikeCount(post.getLikeCount() - 1);
        } else {
            // Adiciona o like
            post.getLikes().add(currentUser.getId());
            post.setLikeCount(post.getLikeCount() + 1);
        }

        return postRepository.save(post);
    }

    public Post addComment(String postId, CommentRequest request) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post não encontrado"));

        Comment comment = Comment.builder()
                .id(UUID.randomUUID().toString())
                .userId(currentUser.getId())
                .username(currentUser.getUsername())
                .content(request.getContent())
                .createdAt(LocalDateTime.now())
                .build();

        post.getComments().add(comment);
        return postRepository.save(post);
    }

    public Post removeComment(String postId, String commentId) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post não encontrado"));

        Comment comment = post.getComments().stream()
                .filter(c -> c.getId().equals(commentId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Comentário não encontrado"));

        // Verifica se o usuário é o dono do comentário
        if (!comment.getUserId().equals(currentUser.getId())) {
            throw new UnauthorizedException("Você não tem permissão para remover este comentário");
        }

        post.getComments().removeIf(c -> c.getId().equals(commentId));
        return postRepository.save(post);
    }

    public PostAuthorResponse getPostAuthor(String postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post não encontrado"));
        
        User author = userRepository.findById(post.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Autor não encontrado"));
                
        return new PostAuthorResponse(
            author.getUsername(),
            author.getProfileImage()
        );
    }
} 