package com.victoryboard.main.repository;

import com.victoryboard.main.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findByAssigneesId(Long userId);
    List<Task> findByTeamId(Long teamId);
}
