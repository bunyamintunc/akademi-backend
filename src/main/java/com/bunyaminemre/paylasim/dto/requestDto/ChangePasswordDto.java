package com.bunyaminemre.paylasim.dto.requestDto;


import lombok.Data;

@Data
public class ChangePasswordDto {

    String token;
    String newPassword;
}
