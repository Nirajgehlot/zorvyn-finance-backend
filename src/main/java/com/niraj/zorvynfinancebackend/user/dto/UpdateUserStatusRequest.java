package com.niraj.zorvynfinancebackend.user.dto;

import com.niraj.zorvynfinancebackend.user.enums.UserStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserStatusRequest {

    private UserStatus status;
}