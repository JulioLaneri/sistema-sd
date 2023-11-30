package com.sd.sistemasd.service.Email;

public interface IEmailService {
    void sendMailToMultipleAddresses(String[] to, String subject, String text);
}
