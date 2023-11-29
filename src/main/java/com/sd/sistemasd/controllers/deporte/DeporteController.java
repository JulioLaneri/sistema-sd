package com.sd.sistemasd.controllers.deporte;


import com.sd.sistemasd.controllers.suscripcion.SuscripcionController;
import com.sd.sistemasd.dto.deporte.DeporteDTO;
import com.sd.sistemasd.service.Deporte.IDeporteService;
import com.sd.sistemasd.utils.Setting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deportes")
public class DeporteController {

    private static final Logger logger = LoggerFactory.getLogger(SuscripcionController.class);
    @Autowired
    IDeporteService deporteService;

    @PostMapping("/save")
    @PreAuthorize("hasRole('EMPLEADO')")
    public DeporteDTO createDeporte(@RequestBody DeporteDTO deporteDTO) {
        return deporteService.createDeporte(deporteDTO);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('EMPLEADO')")
    public DeporteDTO getDeporteById(@PathVariable Long id) {
        logger.warn("Guardando en cache el deporte con id = " + id);
        return deporteService.getDeporteById(id);
    }

    @GetMapping("/page/{page}")
    @PreAuthorize("hasRole('EMPLEADO')")
    public List<DeporteDTO> getAllDeportes(@PathVariable(name = "page") int page) {
        int size = Setting.PAGE_SIZE;
        Pageable pageable = PageRequest.of(page, size);
        return deporteService.getAllDeportes(pageable).getContent();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('EMPLEADO')")
    public DeporteDTO updateDeporte(@PathVariable Long id, @RequestBody DeporteDTO deporteDTO) {
        logger.warn("Actualizando en cache el deporte con id = " + id);
        return deporteService.updateDeporte(id, deporteDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('EMPLEADO')")
    public void deleteDeporte(@PathVariable Long id) {
        logger.warn("Borrando de cache el deporte con id = " + id);
        deporteService.deleteDeporte(id);
    }
}
