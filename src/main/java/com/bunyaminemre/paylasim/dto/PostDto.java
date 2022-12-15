package com.bunyaminemre.paylasim.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PostDto {
    MultipartFile uploadFile;
    Long userId;
    String description;
}
