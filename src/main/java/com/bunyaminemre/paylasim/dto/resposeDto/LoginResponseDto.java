package com.bunyaminemre.paylasim.dto.resposeDto;

import lombok.Data;

@Data
public class LoginResponseDto {

    Long id;
    String jwt;
    String name;
    String surname;
}
