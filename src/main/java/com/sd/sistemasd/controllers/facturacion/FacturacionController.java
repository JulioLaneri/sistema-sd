package com.sd.sistemasd.controllers.facturacion;

import com.sd.sistemasd.dto.facturacion.cliente.FacturacionClienteDTO;
import com.sd.sistemasd.service.facturacion.cliente.IFactClienteDetallesService;
import com.sd.sistemasd.service.facturacion.cliente.IFacturaClienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/factura-cliente")
public class FacturacionController {

    private static final Logger logger = LoggerFactory.getLogger(FacturacionController.class);

    @Autowired
    IFacturaClienteService facturaService;

    @Autowired
    IFactClienteDetallesService detallesService;

    @PostMapping("/save")
    public ResponseEntity<?> createFactura(@RequestBody FacturacionClienteDTO facturaDTO){
        try {
            Date fechaActual = new Date();
            facturaDTO.setFecha(fechaActual);

            facturaDTO = facturaService.create(facturaDTO);
            logger.info("Factura creada correctamente {}", facturaDTO);

            // Devuelve la facturaDTO en la respuesta
            return ResponseEntity.status(HttpStatus.CREATED).body(facturaDTO);
        } catch (HttpMessageNotReadableException ex) {
            logger.error("Error de lectura del cuerpo de la solicitud");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error de lectura del cuerpo de la solicitud");
        } catch (DataAccessException ex) {
            logger.error("Error de base de datos al crear la factura.", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error de base de datos al crear la factura.");
        } catch (AccessDeniedException ex) {
            logger.error("Acceso denegado - el usuario no tiene permisos de administrador", ex);
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Acceso denegado - el usuario no tiene permisos de administrador");
        }
    }



}
