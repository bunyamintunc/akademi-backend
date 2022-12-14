package com.bunyaminemre.paylasim.controller;

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
    public User createUser(@RequestBody User user){
        return userService.saveUser(user);
    }

    @GetMapping
    public List<User> getUsers(){
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
