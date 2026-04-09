package com.niraj.zorvynfinancebackend.user.dto;

import com.niraj.zorvynfinancebackend.user.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRoleRequest {

    private UserRole role;
}