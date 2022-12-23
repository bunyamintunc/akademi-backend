package com.bunyaminemre.paylasim.controller;

import com.bunyaminemre.paylasim.config.security.JwtTokenProvider;
import com.bunyaminemre.paylasim.dto.LoginDto;
import com.bunyaminemre.paylasim.dto.PasswordResetDto;
import com.bunyaminemre.paylasim.dto.requestDto.ChangePasswordDto;
import com.bunyaminemre.paylasim.dto.requestDto.UserRequestDto;
import com.bunyaminemre.paylasim.dto.resposeDto.LoginResponseDto;
import com.bunyaminemre.paylasim.dto.resposeDto.Message;
import com.bunyaminemre.paylasim.service.AuthService;
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




    @Autowired
    public AuthController(AuthService authService, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
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
        response.setJwt(jwtToken);
        response.setName("deneme");
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
