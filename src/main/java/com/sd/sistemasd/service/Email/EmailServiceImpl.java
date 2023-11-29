package com.sd.sistemasd.service.Email;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.beans.SimpleBeanInfo;

@Service
public class EmailServiceImpl implements IEmailService{
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendMail(String to, String subject, String text) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(to);
        msg.setSubject(subject);
        msg.setText(text);
        mailSender.send(msg);
    }
}
//@Scheduled(fixedDelay)
