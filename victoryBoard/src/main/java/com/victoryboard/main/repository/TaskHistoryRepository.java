package com.victoryboard.main.repository;

import com.victoryboard.main.entities.TaskHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskHistoryRepository extends JpaRepository<TaskHistory, Integer> {
    List<TaskHistory> findByTaskId(int taskId);
    List<TaskHistory> findByChangedById(int userId);
}
