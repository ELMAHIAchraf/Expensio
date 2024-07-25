package com.example.Expenses.Controller;

import com.example.Expenses.Config.MyUser;
import com.example.Expenses.DTO.EmailDTO;
import com.example.Expenses.Service.EmailService.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class EmailController {

    private EmailService emailService;
    @PostMapping("/support")
    public String sendEmail(@RequestBody EmailDTO email) {
        MyUser user = (MyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return emailService.sendSimpleMessage(
                user.getUsername(),
                "elmahi.achraf9@gmail.com",
                email.getSubject(),
                email.getMessage());
    }
}
