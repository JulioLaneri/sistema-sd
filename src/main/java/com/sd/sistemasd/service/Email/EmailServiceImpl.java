package com.sd.sistemasd.service.Email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


import java.io.IOException;

@Service
public class EmailServiceImpl implements IEmailService {

    @Autowired
    private JavaMailSender mailSender;


    @Override
    public void sendMailWithAttachment(String to, String subject, String text, byte[] attachment, String attachmentName) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            // Adjuntar el archivo
            ByteArrayResource resource = new ByteArrayResource(attachment);
            helper.addAttachment(attachmentName, resource);

            mailSender.send(message);
        } catch (MessagingException e) {
            // Manejar errores al enviar el correo con archivo adjunto
            e.printStackTrace();
        }
    }

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
