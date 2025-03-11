package com.victoryboard.main.service;

import com.victoryboard.main.dto.taskhistoryDto.TaskHistoryResponseDto;
import com.victoryboard.main.entities.TaskHistory;
import com.victoryboard.main.repository.TaskHistoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskHistoryService {
    private final TaskHistoryRepository taskHistoryRepository;

    public List<TaskHistoryResponseDto> getTaskHistoryByTaskId(int taskId) {
        List<TaskHistory> historyList = taskHistoryRepository.findByTaskId(taskId);

        if (historyList.isEmpty()) {
            throw new EntityNotFoundException("No history found for task with ID: " + taskId);
        }

        return historyList.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private TaskHistoryResponseDto convertToDto(TaskHistory taskHistory) {
        return new TaskHistoryResponseDto(
                taskHistory.getId(),
                taskHistory.getChangeType(),
                taskHistory.getOldValue(),
                taskHistory.getNewValue(),
                taskHistory.getTask().getId(),
                taskHistory.getChangedBy().getId(),
                taskHistory.getChangedAt()
        );
    }
}
