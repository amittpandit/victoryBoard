package com.victoryboard.main.dto.userDto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class UserResponseDto {
    private int id;
    private String username;
    private String email;
    private Set<String> role; // Set of role names
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}