package com.bunyaminemre.paylasim.dto.resposeDto;
import com.bunyaminemre.paylasim.entitiy.Role;

import lombok.Data;

@Data
public class LoginResponseDto {

    Long id;
    String jwt;
    String name;
    String surname;
    List<Role>  roles;
}
