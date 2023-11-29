package com.sd.sistemasd.service.Deporte;


import com.sd.sistemasd.dto.deporte.DeporteDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IDeporteService {

    DeporteDTO createDeporte(DeporteDTO deporteDTO);
    DeporteDTO getDeporteById(Long id);
    public Page<DeporteDTO> getAllDeportes(Pageable pageable);
    DeporteDTO updateDeporte(Long id, DeporteDTO deporteDTO);
    void deleteDeporte(Long id);
}
