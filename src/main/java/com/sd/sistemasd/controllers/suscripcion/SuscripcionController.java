package com.sd.sistemasd.controllers.suscripcion;

import com.sd.sistemasd.dto.suscripcion.SuscripcionConDetallesDTO;
import com.sd.sistemasd.dto.suscripcion.SuscripcionDTO;
import com.sd.sistemasd.dto.suscripcion.SuscripcionDetalleDTO;
import com.sd.sistemasd.service.Suscripcion.ISuscripcionService;
import com.sd.sistemasd.utils.Setting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/suscripciones")
public class SuscripcionController {

    private static final Logger logger = LoggerFactory.getLogger(SuscripcionController.class);

    @Autowired
    ISuscripcionService suscripcionService;

//    @PostMapping("/crearConDetalles")
//    @PreAuthorize("hasAuthority('ADMIN')")
//    public ResponseEntity<?> createSuscripcionConDetalles(@RequestBody SuscripcionConDetallesDTO suscripcionConDetallesDTO) {
//        try {
//            // Extrae la información de la suscripción
//            SuscripcionDTO suscripcionDTO = suscripcionConDetallesDTO.getSuscripcion();
//            SuscripcionDTO createdSuscripcion = suscripcionService.createSuscripcion(suscripcionDTO);
//
//            // Extrae la información de los detalles de la suscripción
//            List<SuscripcionDetalleDTO> detallesDTO = suscripcionConDetallesDTO.getDetalles();
//            for (SuscripcionDetalleDTO detalleDTO : detallesDTO) {
//                detalleDTO.setSuscripcionID(createdSuscripcion.getSuscripcionID());
//                suscripcionService.createSuscripcionDetalle(detalleDTO);
//            }
//
//            return new ResponseEntity<>(createdSuscripcion, HttpStatus.CREATED);
//        } catch (Exception e) {
//            logger.error("Error al crear la suscripción con detalles", e);
//            return new ResponseEntity<>("error500", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//    }

    @PostMapping("/save")
    public ResponseEntity<?> createSuscripcionConDetalles(
            @RequestBody SuscripcionConDetallesDTO suscripcionConDetallesDTO) {
    try{
        SuscripcionConDetallesDTO createdSuscripcion = suscripcionService.createSuscripcionConDetalles(suscripcionConDetallesDTO);
        return new ResponseEntity<>(createdSuscripcion, HttpStatus.CREATED);
    }catch (Exception e) {
        logger.error("Error al crear la suscripción con detalles", e);
        return new ResponseEntity<>("error500", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }

    @GetMapping("/page/{page}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getAllSuscripciones(@PathVariable int page) {
        int size = Setting.PAGE_SIZE;
        try {
            Pageable pageable = PageRequest.of(page - 1, size);
            List<SuscripcionConDetallesDTO> suscripciones = suscripcionService.getAllSuscripcionesWithDetalles(pageable);

            return new ResponseEntity<>(suscripciones, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            logger.error("Suscripciones no encontradas", e);
            return new ResponseEntity<>("error404", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error al obtener las suscripciones", e);
            return new ResponseEntity<>("error500", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getSuscripcionWithDetallesById(@PathVariable Long id) {
        try {
            SuscripcionDTO suscripcion = suscripcionService.getSuscripcionById(id);

            if (suscripcion != null) {
                List<SuscripcionDetalleDTO> detalles = suscripcionService.getSuscripcionDetalles(id);

                SuscripcionConDetallesDTO suscripcionConDetalles = new SuscripcionConDetallesDTO();
                suscripcionConDetalles.setSuscripcion(suscripcion);
                suscripcionConDetalles.setDetalles(detalles);

                return new ResponseEntity<>(suscripcionConDetalles, HttpStatus.OK);
            }

            throw new ResourceNotFoundException("Suscripción no encontrada");
        } catch (ResourceNotFoundException e) {
            logger.error("Suscripción no encontrada");
            return new ResponseEntity<>("error404", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error al obtener la suscripción con detalles", e);
            return new ResponseEntity<>("error500", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateSuscripcion(@PathVariable Long id, @RequestBody SuscripcionDTO suscripcionDTO) {
        try {
            SuscripcionDTO updatedSuscripcion = suscripcionService.updateSuscripcion(id, suscripcionDTO);

            return new ResponseEntity<>(updatedSuscripcion, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            logger.error("Suscripción no encontrada para actualizar", e);
            return new ResponseEntity<>("error404", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error al actualizar la suscripción", e);
            return new ResponseEntity<>("error500", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteSuscripcion(@PathVariable Long id) {
        try {
            suscripcionService.deleteSuscripcion(id);

            return new ResponseEntity<>("Suscripción eliminada", HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            logger.error("Suscripción no encontrada para eliminar", e);
            return new ResponseEntity<>("error404", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error al eliminar la suscripción", e);
            return new ResponseEntity<>("error500", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{suscripcionId}/detalles")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> createSuscripcionDetalle(@PathVariable Long suscripcionId, @RequestBody SuscripcionDetalleDTO detalleDTO) {
        try {
            SuscripcionDetalleDTO createdDetalle = suscripcionService.createSuscripcionDetalle(detalleDTO);

            return new ResponseEntity<>(createdDetalle, HttpStatus.CREATED);
        } catch (ResourceNotFoundException e) {
            logger.error("Suscripción no encontrada al crear el detalle", e);
            return new ResponseEntity<>("error404", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error al crear el detalle de la suscripción", e);
            return new ResponseEntity<>("error500", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/detalles/{detalleId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteSuscripcionDetalle(@PathVariable Long detalleId) {
        try {
            suscripcionService.deleteSuscripcionDetalle(detalleId);

            return new ResponseEntity<>("Detalle de suscripción eliminado", HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            logger.error("Detalle de suscripción no encontrado para eliminar", e);
            return new ResponseEntity<>("error404", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error al eliminar el detalle de la suscripción", e);
            return new ResponseEntity<>("error500", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}