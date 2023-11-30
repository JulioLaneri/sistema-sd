package com.sd.sistemasd.service.Suscripcion;


import com.sd.sistemasd.dto.suscripcion.SuscripcionConDetallesDTO;
import com.sd.sistemasd.dto.suscripcion.SuscripcionDTO;
import com.sd.sistemasd.dto.suscripcion.SuscripcionDetalleDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ISuscripcionService {
    SuscripcionDTO createSuscripcion(SuscripcionDTO suscripcionDTO);

    public SuscripcionConDetallesDTO createSuscripcionConDetalles(SuscripcionConDetallesDTO suscripcionConDetallesDTO);
    SuscripcionDTO getSuscripcionById(Long id);
    List<SuscripcionDTO> getAllSuscripciones(int page, int size);
    SuscripcionDTO updateSuscripcion(Long id, SuscripcionDTO suscripcionDTO);
    void deleteSuscripcion(Long id);
    SuscripcionDetalleDTO createSuscripcionDetalle(SuscripcionDetalleDTO detalleDTO);
    void deleteSuscripcionDetalle(Long detalleId);
    List<SuscripcionDetalleDTO> getSuscripcionDetalles(Long suscripcionId);

    List<SuscripcionConDetallesDTO> getAllSuscripcionesWithDetalles(Pageable pageable);



}