package com.backend.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostAuthorResponse {
    private String username;
    private String profilePicture;
} 