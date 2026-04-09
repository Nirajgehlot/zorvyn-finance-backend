package com.niraj.zorvynfinancebackend.user.service;

import com.niraj.zorvynfinancebackend.common.exception.ResourceNotFoundException;
import com.niraj.zorvynfinancebackend.user.dto.CreateUserRequest;
import com.niraj.zorvynfinancebackend.user.dto.UpdateUserRoleRequest;
import com.niraj.zorvynfinancebackend.user.dto.UpdateUserStatusRequest;
import com.niraj.zorvynfinancebackend.user.dto.UserResponse;
import com.niraj.zorvynfinancebackend.user.entity.User;
import com.niraj.zorvynfinancebackend.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.niraj.zorvynfinancebackend.security.model.RegisterRequest;
import com.niraj.zorvynfinancebackend.user.enums.UserRole;
import com.niraj.zorvynfinancebackend.user.enums.UserStatus;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse createUser(CreateUserRequest request) {

        User user = new User(
                request.getName(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getRole(),
                request.getStatus()
        );

        User saved = repository.save(user);

        return new UserResponse(
                saved.getId(),
                saved.getName(),
                saved.getEmail(),
                saved.getRole(),
                saved.getStatus()
        );
    }

    public UserResponse registerFirstAdmin(RegisterRequest request) {

        if (repository.count() > 0) {
            throw new RuntimeException("Public registration is disabled. Please contact admin.");
        }

        User user = new User(
                request.getName(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                UserRole.ADMIN,
                UserStatus.ACTIVE
        );

        User saved = repository.save(user);

        return new UserResponse(
                saved.getId(),
                saved.getName(),
                saved.getEmail(),
                saved.getRole(),
                saved.getStatus()
        );
    }

    public List<UserResponse> getAllUsers() {
        return repository.findAll()
                .stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getRole(),
                        user.getStatus()
                ))
                .toList();
    }

    public UserResponse getUserById(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getStatus()
        );
    }

    public UserResponse updateUserRole(Long id, UpdateUserRoleRequest request) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setRole(request.getRole());

        User updated = repository.save(user);

        return new UserResponse(
                updated.getId(),
                updated.getName(),
                updated.getEmail(),
                updated.getRole(),
                updated.getStatus()
        );
    }

    public UserResponse updateUserStatus(Long id, UpdateUserStatusRequest request) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setStatus(request.getStatus());

        User updated = repository.save(user);

        return new UserResponse(
                updated.getId(),
                updated.getName(),
                updated.getEmail(),
                updated.getRole(),
                updated.getStatus()
        );
    }
}