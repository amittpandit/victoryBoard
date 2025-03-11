package com.victoryboard.main.dto.taskhistoryDto;

import com.victoryboard.main.entities.TaskHistory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskHistoryResponseDto {
    private int id;          // Changed from Integer to int
    private String changeType;
    private String oldValue;
    private String newValue;
    private int taskId;      // Changed from Integer to int
    private int userId;      // Changed from Integer to int
    private LocalDateTime changedAt;
}
