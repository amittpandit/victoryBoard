package com.victoryboard.main.controller;

import com.victoryboard.main.dto.taskDto.TaskRequestDto;
import com.victoryboard.main.dto.taskDto.TaskResponseDto;
import com.victoryboard.main.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping("/{userId}")
    public ResponseEntity<TaskResponseDto> createTask(
            @RequestBody TaskRequestDto taskRequestDto,
            @PathVariable int userId) {
        return ResponseEntity.ok(taskService.createTask(taskRequestDto, userId));
    }

    @PutMapping("/{taskId}/{userId}")
    public ResponseEntity<TaskResponseDto> updateTask(
            @PathVariable int taskId,
            @RequestBody TaskRequestDto taskRequestDto,
            @PathVariable int userId) {
        return ResponseEntity.ok(taskService.updateTask(taskId, taskRequestDto, userId));
    }

    @DeleteMapping("/{taskId}/{userId}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable int taskId,
            @PathVariable int userId) {
        taskService.deleteTask(taskId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponseDto> getTaskById(@PathVariable int taskId) {
        return ResponseEntity.ok(taskService.getTaskById(taskId));
    }
}
