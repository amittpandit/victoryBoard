package com.victoryboard.main.controller;

import com.victoryboard.main.dto.taskhistoryDto.TaskHistoryResponseDto;
import com.victoryboard.main.service.TaskHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task-history")
@RequiredArgsConstructor
public class TaskHistoryController {
    private final TaskHistoryService taskHistoryService;

    @GetMapping("/{taskId}")
    public ResponseEntity<List<TaskHistoryResponseDto>> getTaskHistory(@PathVariable int taskId) {
        return ResponseEntity.ok(taskHistoryService.getTaskHistoryByTaskId(taskId));
    }
}
