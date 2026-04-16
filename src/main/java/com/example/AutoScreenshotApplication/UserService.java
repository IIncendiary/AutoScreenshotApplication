package com.example.AutoScreenshotApplication;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDto> findAllUsers() {
        List<UserModel> userModelList = userRepository.findAll();
        return userModelList.stream().map(userMapper::toDto).toList();
    }

    @Transactional
    public void createUser(UserDto userDto) {
        userRepository.save(userMapper.toModel(userDto));
    }

    public UserDto getUserById(Long id) {
        return userMapper.toDto(userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No user with such id" + id)));
    }

    @Transactional
    public void updateUser(Long id,UserDto userDto){
        UserModel existingUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No user with such id" + id));
        userMapper.updateEntityFromDto(userDto,existingUser);
        userRepository.save(existingUser);
    }

    @Transactional
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

}
