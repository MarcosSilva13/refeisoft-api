package com.refeisoft.domain.service;

import com.refeisoft.api.dto.PageResponseDTO;
import com.refeisoft.api.dto.PasswordRequesDTO;
import com.refeisoft.api.dto.UpdateUserRequestDTO;
import com.refeisoft.api.dto.UserResponseDTO;
import com.refeisoft.api.mapper.UserMapper;
import com.refeisoft.domain.entity.User;
import com.refeisoft.domain.repository.UserRepository;
import com.refeisoft.infra.exception.DuplicateAttributeException;
import com.refeisoft.infra.exception.MismatchedPasswordException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private static final int PAGE_SIZE = 10;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public PageResponseDTO getAllUsers(int page) {
        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        PageRequest pageRequest = PageRequest.of(page, PAGE_SIZE, sort);
        Page<UserResponseDTO> userPage = userRepository.findAll(pageRequest).map(userMapper::toUserResponseDTO);

        return new PageResponseDTO(userPage.getContent(), userPage.getTotalPages());
    }

    @Transactional
    public UserResponseDTO updateUser(UpdateUserRequestDTO requestDTO) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean emailChanged = !requestDTO.email().equals(user.getEmail());
        if (emailChanged) {
            verifyEmail(requestDTO.email());
        }

        userMapper.toUpdateUser(requestDTO, user);
        User userUpdated = userRepository.save(user);
        return userMapper.toUserResponseDTO(userUpdated);
    }

    private void verifyEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateAttributeException("O email informado já existe.");
        }
    }

    @Transactional
    public void updateUserPassword(PasswordRequesDTO requesDTO) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!passwordEncoder.matches(requesDTO.currentPassword(), user.getPassword())) {
            throw new MismatchedPasswordException("Senha atual incompatível.");
        }

        user.setPassword(passwordEncoder.encode(requesDTO.newPassword()));
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = getUserById(userId);
        userRepository.delete(user);
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));
    }
}
