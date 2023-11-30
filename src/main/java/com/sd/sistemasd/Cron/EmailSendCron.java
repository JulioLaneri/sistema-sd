package com.sd.sistemasd.Cron;

import com.sd.sistemasd.service.Cliente.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailSendCron {

   @Autowired
    IClienteService clienteService;

    //@Scheduled(fixedDelay = 5000)
    public void sendMail(){
        clienteService.enviarCorreoATodos("Prueba", "Aviso");

    }
}
