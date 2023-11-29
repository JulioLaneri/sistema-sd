package com.sd.sistemasd.Cron;

import com.sd.sistemasd.service.Email.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailSendCron {

    @Autowired
    IEmailService emailService;

    //@Scheduled(fixedDelay = 60000)
    public void sendMail(){
        emailService.sendMail("juliobenitezlaneri@gmail.com", "PRUEBA", "Probando CRON");
    }
}
