package com.example.AutoScreenshotApplication;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable Long userId){
        return userService.getUserById(userId);
    }

    @PostMapping("/create")
    public void createUser(@RequestBody UserDto userDto){
        userService.createUser(userDto);
    }

    @PutMapping("/{userId}/update")
    public void updateUser(@PathVariable Long userId,@RequestBody UserDto userDto){
        userService.updateUser(userId,userDto);
    }

    @DeleteMapping("/{userId}/delete")
    public void deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
    }
}
