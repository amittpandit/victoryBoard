package com.victoryboard.main.controller;

import com.victoryboard.main.dto.teamDto.TeamRequestDto;
import com.victoryboard.main.dto.teamDto.TeamResponseDto;
import com.victoryboard.main.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;

    @PostMapping("/{adminId}")
    public ResponseEntity<TeamResponseDto> createTeam(
            @RequestBody TeamRequestDto teamRequestDto,
            @PathVariable int adminId) {
        return ResponseEntity.ok(teamService.createTeam(teamRequestDto, adminId));
    }

    @PutMapping("/{teamId}/{adminId}")
    public ResponseEntity<TeamResponseDto> updateTeam(
            @PathVariable int teamId,
            @RequestBody TeamRequestDto teamRequestDto,
            @PathVariable int adminId) {
        return ResponseEntity.ok(teamService.updateTeam(teamId, teamRequestDto, adminId));
    }

    @GetMapping("/{teamId}")
    public ResponseEntity<TeamResponseDto> getTeamById(@PathVariable int teamId) {
        return ResponseEntity.ok(teamService.getTeamById(teamId));
    }

    @GetMapping("/name/{teamName}")
    public ResponseEntity<TeamResponseDto> getTeamByName(@PathVariable String teamName) {
        return ResponseEntity.ok(teamService.getTeamByName(teamName));
    }

    @GetMapping("/{teamId}/members")
    public ResponseEntity<List<Integer>> getTeamMembers(@PathVariable int teamId) {
        return ResponseEntity.ok(teamService.getTeamMembers(teamId));
    }
}
