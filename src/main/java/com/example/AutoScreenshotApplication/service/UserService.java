package com.example.AutoScreenshotApplication.service;


import com.example.AutoScreenshotApplication.dto.UserDto;
import com.example.AutoScreenshotApplication.mapper.UserMapper;
import com.example.AutoScreenshotApplication.entity.User;
import com.example.AutoScreenshotApplication.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public List<UserDto> findAllUsers() {
        List<User> userModelList = userRepository.findAll();
        return userModelList.stream().map(userMapper::toDto).toList();
    }

    @Transactional
    public void createUser(UserDto userDto) {
        userRepository.save(userMapper.toEntity(userDto));
    }

    @Transactional(readOnly = true)
    public UserDto getUserById(Long id) {
        return userMapper.toDto(userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No user with such id" + id)));
    }

    @Transactional
    public void updateUser(UserDto userDto) {
        userRepository.save(userMapper.toEntity(userDto));
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
