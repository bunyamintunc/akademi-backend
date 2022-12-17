package com.bunyaminemre.paylasim.dto.requestDto;

import lombok.Data;

@Data
public class UserRequestDto {

    private String name;
    private String surname;
    private String email;
    private String password;

    private Long roleId;

}
