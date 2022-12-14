package com.bunyaminemre.paylasim.entitiy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationEmail {


    private String to;

    private String from;

    private String subject;

    private String text;

    private String template;

    Map<String, Object> properties;


    /*

    private String subject;
    private String recipient;
    private String body;

     */
}

