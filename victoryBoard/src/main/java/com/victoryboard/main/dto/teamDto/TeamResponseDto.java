package com.victoryboard.main.dto.teamDto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TeamResponseDto {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<Integer> memberIds; // IDs of team members
    private List<Integer> taskIds;

    public TeamResponseDto(long id, String name, String description, LocalDateTime createdDate, LocalDateTime lastModifiedDate, List<Integer> memberIds, List<Integer> taskIds) {
    }
}
