package com.sd.sistemasd.service.Email;

public interface IEmailService {
    public void sendMail(String to, String subject, String text);
}
