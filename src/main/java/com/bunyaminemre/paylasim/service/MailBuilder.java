package com.bunyaminemre.paylasim.service;

import lombok.AllArgsConstructor;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@AllArgsConstructor
public class MailBuilder {
    private final TemplateEngine templateEngine;

    String build(String message){
        Context context = new Context();
        context.setVariable("message",message);
        return  templateEngine.process("mailTemplate",context);
    }
}