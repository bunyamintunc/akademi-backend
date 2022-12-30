package com.bunyaminemre.paylasim.service;

import com.bunyaminemre.paylasim.dto.requestDto.UserRequestDto;
import com.bunyaminemre.paylasim.dto.resposeDto.UserResponseDto;
import com.bunyaminemre.paylasim.entitiy.Post;
import com.bunyaminemre.paylasim.entitiy.Role;
import com.bunyaminemre.paylasim.entitiy.User;
import com.bunyaminemre.paylasim.repository.RoleRepository;
import com.bunyaminemre.paylasim.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;


    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User saveUser(UserRequestDto userDto){
        User user = User.builder()
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .email(userDto.getEmail())
                .isActive(false)
                .roles(new HashSet<Role>())
                .username("@"+userDto.getName()+userDto.getSurname())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .posts(new HashSet<Post>()).build();
        Role role = roleRepository.findById(1L).orElse(null);
        user.getRoles().add(role);
        return userRepository.save(user);
    }

    public List<UserResponseDto> getListUser() {

        List<User> userList = userRepository.findAll();
        return converUserToUserResponseDto(userList);

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

    public List<UserResponseDto> converUserToUserResponseDto(List<User> users){

        List<UserResponseDto> userDtoList = new ArrayList<>();
        UserResponseDto dto;
        for ( User user : users){
            dto = new UserResponseDto();
            dto.setUserName(user.getUsername());
            dto.setName(user.getName());
            dto.setEmail(user.getEmail());
            dto.setId(user.getId());
            dto.setSurname(user.getSurname());
            dto.setStatus(String.valueOf(user.isActive()));
            userDtoList.add(dto);
        }
       return userDtoList;
    };
}
