package com.victoryboard.main.service;

import com.victoryboard.main.dto.userDto.UserRequestDto;
import com.victoryboard.main.dto.userDto.UserResponseDto;
import com.victoryboard.main.entities.Task;
import com.victoryboard.main.entities.Team;
import com.victoryboard.main.entities.User;
import com.victoryboard.main.enums.Role;
import com.victoryboard.main.repository.TaskRepository;
import com.victoryboard.main.repository.TeamRepository;
import com.victoryboard.main.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final TeamRepository teamRepository;


    @Transactional
    public UserResponseDto createUser(UserRequestDto requestDto) {
        User user = convertToUser(requestDto);
        User savedUser = userRepository.save(user);
        return convertToUserResponseDto(savedUser);
    }

    @Transactional
    public UserResponseDto assignUserToTasks(int userId, List<Integer> taskIds) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Task> tasks = taskRepository.findAllById(taskIds);
        if (tasks.size() != taskIds.size()) {
            throw new RuntimeException("One or more tasks not found");
        }

        user.getTasks().addAll(tasks);
        User updatedUser = userRepository.save(user);

        return convertToUserResponseDto(updatedUser);
    }

    @Transactional
    public UserResponseDto assignUserToTeams(int userId, List<Integer> teamIds) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Team> teams = teamRepository.findAllById(teamIds);
        if (teams.size() != teamIds.size()) {
            throw new RuntimeException("One or more teams not found");
        }

        user.getTeams().addAll(teams);
        User updatedUser = userRepository.save(user);

        return convertToUserResponseDto(updatedUser);
    }

    @Transactional
    public UserResponseDto updateUser(int userId, UserRequestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (requestDto.getUsername() != null && !requestDto.getUsername().equals(user.getUsername())) {
            user.setUsername(requestDto.getUsername());
        }
        if (requestDto.getEmail() != null && !requestDto.getEmail().equals(user.getEmail())) {
            user.setEmail(requestDto.getEmail());
        }
        if (requestDto.getPassword() != null && !requestDto.getPassword().equals(user.getPassword())) {
            user.setPassword(requestDto.getPassword()); // Should be hashed in practice
        }
        if (requestDto.getRole() != null && !requestDto.getRole().equalsIgnoreCase(user.getRole().name())) {
            user.setRole(Role.valueOf(requestDto.getRole().toUpperCase()));
        }

        user.setLastModifiedDate(LocalDateTime.now());
        User updatedUser = userRepository.save(user);

        return convertToUserResponseDto(updatedUser);
    }

    @Transactional
    public void deleteUser(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.getTasks().forEach(task -> task.getUsers().remove(user));
        user.getTeams().forEach(team -> team.getUsers().remove(user));

        userRepository.delete(user);
    }

    public User convertToUser(UserRequestDto dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole(Role.valueOf(dto.getRole().toUpperCase()));
        user.setCreatedDate(LocalDateTime.now());
        user.setLastModifiedDate(LocalDateTime.now());
        return user;
    }

    public UserResponseDto convertToUserResponseDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(Set.of(user.getRole().name()));
        dto.setCreatedAt(user.getCreatedDate());
        dto.setUpdatedAt(user.getLastModifiedDate());
        return dto;
    }

    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToUserResponseDto)
                .collect(Collectors.toList());
    }

    public UserResponseDto getUserById(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return convertToUserResponseDto(user);
    }
}