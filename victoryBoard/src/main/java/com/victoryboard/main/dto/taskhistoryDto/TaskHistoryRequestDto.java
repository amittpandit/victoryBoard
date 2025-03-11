package com.victoryboard.main.dto.taskhistoryDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskHistoryRequestDto {
    private String changeType; // e.g., "Status Updated", "Priority Changed"
    private String oldValue; // Previous value (optional)
    private String newValue; // New value (optional)
    private int taskId;
    private int userId;
}
