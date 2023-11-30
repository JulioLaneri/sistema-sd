package com.sd.sistemasd.service.Email;

public interface IEmailService {
    public void sendMailWithAttachment(String to, String subject, String text, byte[] attachment, String attachmentName);
    public void sendMail(String to, String subject, String text);
}
