package com.niraj.zorvynfinancebackend.user.dto;

import com.niraj.zorvynfinancebackend.user.enums.UserRole;
import com.niraj.zorvynfinancebackend.user.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponse {

    private Long id;
    private String name;
    private String email;
    private UserRole role;
    private UserStatus status;
}