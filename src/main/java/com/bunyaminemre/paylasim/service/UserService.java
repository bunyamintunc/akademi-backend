package com.bunyaminemre.paylasim.service;

import com.bunyaminemre.paylasim.entitiy.User;
import com.bunyaminemre.paylasim.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public List<User> getListUser() {
        return userRepository.findAll();
    }

    public List<User> isThereEmail(String email){
        return userRepository.findByEmail(email);
    }

    public void deleteUser(Long userId){
        userRepository.deleteById(userId);
    }

    public User getUserByName(String username) {
        return userRepository.findByUsername(username);
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
