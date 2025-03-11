package com.victoryboard.main.service;

import com.victoryboard.main.dto.teamDto.TeamRequestDto;
import com.victoryboard.main.dto.teamDto.TeamResponseDto;
import com.victoryboard.main.entities.Task;
import com.victoryboard.main.entities.Team;
import com.victoryboard.main.entities.User;
import com.victoryboard.main.enums.Role;
import com.victoryboard.main.repository.TeamRepository;
import com.victoryboard.main.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public TeamResponseDto createTeam(TeamRequestDto teamRequestDto, int adminId) {
        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new EntityNotFoundException("Admin not found"));

        if (admin.getRole() != Role.ADMIN) {
            throw new IllegalStateException("Only admins can create teams");
        }

        Team team = new Team();
        team.setName(teamRequestDto.getName());
        team.setDescription(teamRequestDto.getDescription());
        team.setUsers(userRepository.findAllById(teamRequestDto.getMemberIds()));

        Team savedTeam = teamRepository.save(team);
        return convertToResponseDto(savedTeam);
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public TeamResponseDto updateTeam(int teamId, TeamRequestDto teamRequestDto, int adminId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new EntityNotFoundException("Team not found"));

        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new EntityNotFoundException("Admin not found"));

        if (admin.getRole() != Role.ADMIN) {
            throw new IllegalStateException("Only admins can update teams");
        }

        team.setName(teamRequestDto.getName());
        team.setDescription(teamRequestDto.getDescription());
        team.setUsers(userRepository.findAllById(teamRequestDto.getMemberIds()));

        Team updatedTeam = teamRepository.save(team);
        return convertToResponseDto(updatedTeam);
    }

    public TeamResponseDto getTeamById(int teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new EntityNotFoundException("Team not found"));
        return convertToResponseDto(team);
    }

    public TeamResponseDto getTeamByName(String teamName) {
        Team team = teamRepository.findByName(teamName)
                .orElseThrow(() -> new EntityNotFoundException("Team not found"));
        return convertToResponseDto(team);
    }

    public List<Integer> getTeamMembers(int teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new EntityNotFoundException("Team not found"));
        return team.getUsers().stream().map(User::getId).collect(Collectors.toList());
    }

    private TeamResponseDto convertToResponseDto(Team team) {
        List<Integer> memberIds = team.getUsers().stream()
                .map(User::getId)
                .collect(Collectors.toList());

        List<Integer> taskIds = team.getTasks().stream()
                .map(Task::getId)
                .collect(Collectors.toList());

        return new TeamResponseDto(
                (long) team.getId(),
                team.getName(),
                team.getDescription(),
                team.getCreatedDate(),
                team.getLastModifiedDate(),
                memberIds,
                taskIds
        );
    }
}
