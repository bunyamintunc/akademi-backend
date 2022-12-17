package com.bunyaminemre.paylasim.controller;

import com.bunyaminemre.paylasim.config.security.JwtTokenProvider;
import com.bunyaminemre.paylasim.dto.LoginDto;
import com.bunyaminemre.paylasim.dto.PasswordResetDto;
import com.bunyaminemre.paylasim.dto.requestDto.UserRequestDto;
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
    public ResponseEntity<String> singUp(@RequestBody UserRequestDto userDto) throws MessagingException {
        return authService.singUp(userDto);
    }

    @GetMapping("/verification/{token}")
    public ResponseEntity<String> verification(@PathVariable("token") String token){
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
    @ApiOperation(value = "User login method")
    public String Login(@RequestBody LoginDto userDto){

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDto.getUsername(),userDto.getPassword());
        Authentication auth = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwtToken  = jwtTokenProvider.generateJwtToken(auth);
        return "Bearer "+jwtToken;

    }

    @PostMapping("/resetparola")
    public ResponseEntity<String> sendParolaResetToken(@RequestBody PasswordResetDto resetDto) throws MessagingException {
        return   authService.resetPassword(resetDto);
    }

    @GetMapping("/resetparola/{token}/{password}")
    public ResponseEntity<String> resetPaswordWithToken(@PathVariable("token") String token,@PathVariable("password") String password){
        return authService.resetPasswordWithToken(token,password);
    }

}
