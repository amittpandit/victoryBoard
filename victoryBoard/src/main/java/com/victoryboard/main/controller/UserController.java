package com.victoryboard.main.controller;

import com.victoryboard.main.dto.userDto.UserRequestDto;
import com.victoryboard.main.dto.userDto.UserResponseDto;
import com.victoryboard.main.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto requestDto) {
        return ResponseEntity.ok(userService.createUser(requestDto));
    }

    @PostMapping("/{userId}/tasks")
    public ResponseEntity<UserResponseDto> assignUserToTasks(
            @PathVariable int userId,
            @RequestBody List<Integer> taskIds) {
        return ResponseEntity.ok(userService.assignUserToTasks(userId, taskIds));
    }

    @PostMapping("/{userId}/teams")
    public ResponseEntity<UserResponseDto> assignUserToTeams(
            @PathVariable int userId,
            @RequestBody List<Integer> teamIds) {
        return ResponseEntity.ok(userService.assignUserToTeams(userId, teamIds));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable int userId,
            @RequestBody UserRequestDto requestDto) {
        return ResponseEntity.ok(userService.updateUser(userId, requestDto));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable int userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable int userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }
}
