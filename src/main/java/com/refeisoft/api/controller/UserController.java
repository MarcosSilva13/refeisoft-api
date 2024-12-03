package com.refeisoft.api.controller;

import com.refeisoft.api.dto.PageResponseDTO;
import com.refeisoft.api.dto.PasswordRequesDTO;
import com.refeisoft.api.dto.UpdateUserRequestDTO;
import com.refeisoft.api.dto.UserResponseDTO;
import com.refeisoft.api.openapi.UserControllerDoc;
import com.refeisoft.domain.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController implements UserControllerDoc {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<PageResponseDTO> getAll(@RequestParam int page) {
        PageResponseDTO pageResponseDTO = userService.getAllUsers(page);
        return ResponseEntity.ok(pageResponseDTO);
    }

    @PutMapping
    public ResponseEntity<UserResponseDTO> update(@RequestBody @Valid UpdateUserRequestDTO requestDTO) {
        UserResponseDTO userResponseDTO = userService.updateUser(requestDTO);
        return ResponseEntity.ok(userResponseDTO);
    }

    @PatchMapping
    public ResponseEntity<Void> updatePassword(@RequestBody @Valid PasswordRequesDTO requesDTO) {
        userService.updateUserPassword(requesDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> delete(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
