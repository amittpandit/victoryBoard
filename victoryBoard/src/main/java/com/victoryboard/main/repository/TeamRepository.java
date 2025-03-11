package com.victoryboard.main.repository;

import com.victoryboard.main.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {
    Optional<Team> findByName(String name);
    Boolean existsByName(String name);

}
