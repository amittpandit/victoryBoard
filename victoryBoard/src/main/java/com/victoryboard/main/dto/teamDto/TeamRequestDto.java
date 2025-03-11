package com.victoryboard.main.dto.teamDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamRequestDto {
    private String name;
    private String description;
    private List<Integer> memberIds;
}
