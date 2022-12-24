package com.bunyaminemre.paylasim.controller;

import com.bunyaminemre.paylasim.config.security.JwtTokenProvider;
import com.bunyaminemre.paylasim.dto.LoginDto;
import com.bunyaminemre.paylasim.dto.PasswordResetDto;
import com.bunyaminemre.paylasim.dto.requestDto.ChangePasswordDto;
import com.bunyaminemre.paylasim.dto.requestDto.UserRequestDto;
import com.bunyaminemre.paylasim.dto.resposeDto.LoginResponseDto;
import com.bunyaminemre.paylasim.dto.resposeDto.Message;
import com.bunyaminemre.paylasim.entitiy.User;
import com.bunyaminemre.paylasim.service.AuthService;
import com.bunyaminemre.paylasim.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/auth")
public class AuthController {


    private AuthService authService;

    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;

    private UserService userService;




    @Autowired
    public AuthController(AuthService authService, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider,UserService userService) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("/singup")
    public Message singUp(@RequestBody UserRequestDto userDto) throws MessagingException {
        Message message = new Message();
        message.setValue( authService.singUp(userDto));
        return  message;
    }

    @GetMapping("/verification/{token}")
    public String verification(@PathVariable("token") String token){
        return authService.verificationUser(token);
    }

    /*

    @PostMapping("/login")
    public String login(@RequestBody LoginDto userlogin){
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userlogin.getUsername(),userlogin.getPassword());
        Authentication auth = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwtToken = jwtTokenProvider.generateJwtToken(auth);

        return "Baarer "+userlogin.getPassword();
    }

     */

    @PostMapping("/login")
    public LoginResponseDto Login(@RequestBody LoginDto userDto){

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDto.getUsername(),userDto.getPassword());
        Authentication auth = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwtToken  = jwtTokenProvider.generateJwtToken(auth);
        LoginResponseDto response = new LoginResponseDto();
        User user = userService.getUserByName(userDto.getUsername());
        response.setJwt(jwtToken);
        response.setName(user.getName());
        response.setSurname(user.surname);
        response.setId(user.getId());
        return response;

    }

    @PostMapping("/resetparola")
    public ResponseEntity<String> sendParolaResetToken(@RequestBody PasswordResetDto resetDto) throws MessagingException {
        return   authService.resetPassword(resetDto);
    }

    @PostMapping("/changeparola")
    public ResponseEntity<String> resetPaswordWithToken(@RequestBody ChangePasswordDto changePasswordDto){
        return authService.resetPasswordWithToken(changePasswordDto);
    }

}
