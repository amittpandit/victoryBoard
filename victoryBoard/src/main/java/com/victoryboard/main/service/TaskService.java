package com.victoryboard.main.service;

import com.victoryboard.main.dto.taskDto.TaskRequestDto;
import com.victoryboard.main.dto.taskDto.TaskResponseDto;
import com.victoryboard.main.entities.Task;
import com.victoryboard.main.entities.TaskHistory;
import com.victoryboard.main.entities.User;
import com.victoryboard.main.repository.TaskHistoryRepository;
import com.victoryboard.main.repository.TaskRepository;
import com.victoryboard.main.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskHistoryRepository taskHistoryRepository;

    @Transactional
    public TaskResponseDto createTask(TaskRequestDto taskRequestDto, int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Task task = new Task();
        task.setTitle(taskRequestDto.getTitle());
        task.setDescription(taskRequestDto.getDescription());
        task.setPriority(taskRequestDto.getPriority());
        task.setStatus(taskRequestDto.getStatus());
        task.setDeadline(taskRequestDto.getDeadline());
        task.getUsers().add(user);

        Task savedTask = taskRepository.save(task);

        TaskHistory history = new TaskHistory();
        history.setTask(savedTask);
        history.setChangedBy(user);
        history.setChangeType("Task Created");
        history.setOldValue(null);
        history.setNewValue("New Task Created");
        history.setChangedAt(LocalDateTime.now());

        taskHistoryRepository.save(history);
        return convertToResponseDto(savedTask);
    }

    @Transactional
    public TaskResponseDto updateTask(int taskId, TaskRequestDto taskRequestDto, int userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));

        if (task.getUsers().stream().noneMatch(user -> user.getId() == userId)) {
            throw new IllegalStateException("User is not authorized to update this task");
        }

        task.setTitle(taskRequestDto.getTitle());
        task.setDescription(taskRequestDto.getDescription());
        task.setPriority(taskRequestDto.getPriority());
        task.setStatus(taskRequestDto.getStatus());
        task.setDeadline(taskRequestDto.getDeadline());

        Task updatedTask = taskRepository.save(task);
        return convertToResponseDto(updatedTask);
    }

    @Transactional
    public void deleteTask(int taskId, int userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));

        if (task.getUsers().stream().noneMatch(user -> user.getId() == userId)) {
            throw new IllegalStateException("User is not authorized to delete this task");
        }

        taskRepository.delete(task);
    }

    public TaskResponseDto getTaskById(int taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));
        return convertToResponseDto(task);
    }

    private TaskResponseDto convertToResponseDto(Task task) {
        List<Integer> assigneeIds = task.getUsers().stream()
                .map(User::getId)
                .collect(Collectors.toList());

        return new TaskResponseDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getPriority(),
                task.getStatus(),
                task.getDeadline(),
                task.getCreatedDate(),
                task.getLastModifiedDate(),
                assigneeIds,
                task.getTeam() != null ? task.getTeam().getId() : null
        );
    }
}
