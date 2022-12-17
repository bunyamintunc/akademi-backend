package com.bunyaminemre.paylasim.controller;

import com.bunyaminemre.paylasim.dto.requestDto.UserRequestDto;
import com.bunyaminemre.paylasim.dto.resposeDto.UserResponseDto;
import com.bunyaminemre.paylasim.entitiy.User;
import com.bunyaminemre.paylasim.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public User createUser(@RequestBody UserRequestDto userDto){
        return userService.saveUser(userDto);
    }

    @GetMapping("/getall")
    public List<UserResponseDto> getUsers(){
        return userService.getListUser();
    }

    @GetMapping("/get/{username}")
    public User getUserByName(@PathVariable("username") String username){
        return  userService.getUserByName(username);
    }

    @GetMapping("/getbyid/{id}")
    public User getUserById(@PathVariable("id") Long id){
        return userService.getById(id);
    }


}
