package com.victoryboard.main.dto.taskDto;

import com.victoryboard.main.entities.Task;
import com.victoryboard.main.enums.Priority;
import com.victoryboard.main.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponseDto {
    private int id;
    private String title;
    private String description;
    private Priority priority;
    private Status status;
    private LocalDateTime deadline;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<Integer> assigneeIds; // List of user IDs assigned to the task
    private Integer teamId;
}
