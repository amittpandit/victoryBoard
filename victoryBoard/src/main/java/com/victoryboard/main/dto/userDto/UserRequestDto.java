package com.victoryboard.main.dto.userDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {
    private String username;
    private String email;
    private String password;
    private String role; // Will be converted to User.UserRole in service
}