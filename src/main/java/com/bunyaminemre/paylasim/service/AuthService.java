package com.bunyaminemre.paylasim.service;

import com.bunyaminemre.paylasim.dto.PasswordResetDto;
import com.bunyaminemre.paylasim.dto.requestDto.ChangePasswordDto;
import com.bunyaminemre.paylasim.dto.requestDto.UserRequestDto;
import com.bunyaminemre.paylasim.entitiy.NotificationEmail;
import com.bunyaminemre.paylasim.entitiy.Role;
import com.bunyaminemre.paylasim.entitiy.User;
import com.bunyaminemre.paylasim.entitiy.VerificationToken;
import com.bunyaminemre.paylasim.repository.RoleRepository;
import com.bunyaminemre.paylasim.repository.UserRepository;
import com.bunyaminemre.paylasim.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.*;

@Service
public class AuthService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;



    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public String singUp(UserRequestDto userDto) throws MessagingException {

        List<User> isThereUser = userRepository.findByEmail(userDto.getEmail());
        if (isThereUser.size()>0){
            return "Email is already exist";
        }else{
            Role role = roleService.findRoleByName("ANONİM");
            User user = User.builder()
                    .email(userDto.getEmail())
                    .name(userDto.getName())
                    .surname(userDto.getSurname())
                    .username("@"+userDto.getName()+userDto.getSurname())
                    .password(passwordEncoder.encode(userDto.getPassword()))
                    .roles(new HashSet<>())
                    .isActive(false)
                    .build();
            user.getRoles().add(role);
            User newUser = userRepository.save(user);
            String token =  createVerificationToken(newUser);

       /*
       mailService.sendEmail(new NotificationEmail("please active your accunt",
               user.getEmail(),"http://localhost/auth/verification/"+token));

        */
            NotificationEmail email = new NotificationEmail();
            email.setTo(userDto.getEmail());
            email.setFrom("bunyamintuncc@gmail.com");
            email.setSubject("welcome to"+userDto.getName());
            //email.setLink("http://localhost:8080/auth/verification/"+token);
            Map<String, Object> properties = new HashMap<>();
            properties.put("name",userDto.getName());
            properties.put("link","http://localhost:8080/auth/verification/"+token);
            email.setProperties(properties);
            email.setTemplate("mailTemplate.html");

            mailService.sendHtmlMessage(email);

            return "you are sing up";
        }

    }

    public String createVerificationToken(User user){
        String uuid = UUID.randomUUID().toString();
        VerificationToken token = VerificationToken.builder().token(uuid).user(user).build();
        tokenRepository.save(token);
        return uuid;
    }

    public String verificationUser(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        User user = verificationToken.getUser();
        if (user != null){
            user.setActive(true);
            userRepository.save(user);
        }
        return "doğrulama başarılı";
    }

    public ResponseEntity<String> resetPassword(PasswordResetDto resetDto) throws MessagingException {
        User user = userRepository.findByUsernameAndEmail(resetDto.getUsername(),resetDto.getEmail());
        if (user != null){
            String token = createVerificationToken(user);
            NotificationEmail email = new NotificationEmail();
            email.setTo(user.getEmail());
            email.setFrom("bunyamintuncc@gmail.com");
            email.setSubject("welcome to"+user.getName());
            //email.setLink("http://localhost:8080/auth/verification/"+token);
            Map<String, Object> properties = new HashMap<>();
            properties.put("name",user.getName());
            properties.put("link","http://localhost:3000/reset-password-page/"+token);
            email.setProperties(properties);
            email.setTemplate("mailTemplate.html");

            mailService.sendHtmlMessage(email);
            return new ResponseEntity<>("Şifreinizi değiştirmeniz için emailinze bir baglanti yolladık",HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Kullanici adı veya email yanlış",HttpStatus.OK);

        }
    }

    public ResponseEntity<String> resetPasswordWithToken(ChangePasswordDto changePasswordDto) {
        VerificationToken isThereToken = verificationTokenRepository.findByToken(changePasswordDto.getToken());
        if (isThereToken != null){
            User user = isThereToken.getUser();
            user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
            userRepository.save(user);
            return new ResponseEntity<>("Şifreniz Değiştirildi",HttpStatus.OK);
        }
        return new ResponseEntity<>("Beklenmedik bir hata oluştu",HttpStatus.OK);
    }


}
