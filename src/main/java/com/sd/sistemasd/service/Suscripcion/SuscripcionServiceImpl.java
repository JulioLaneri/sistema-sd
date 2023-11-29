package com.sd.sistemasd.service.Suscripcion;

import com.sd.sistemasd.beans.cliente.ClienteBean;
import com.sd.sistemasd.beans.deporte.DeporteBean;
import com.sd.sistemasd.beans.suscripcion.SuscripcionBean;
import com.sd.sistemasd.beans.suscripcion.SuscripcionDetalleBean;
import com.sd.sistemasd.dao.cliente.ClienteDAO;
import com.sd.sistemasd.dao.deporte.DeporteDAO;
import com.sd.sistemasd.dao.suscripcion.SuscripcionDAO;
import com.sd.sistemasd.dao.suscripcion.SuscripcionDetalleDAO;
import com.sd.sistemasd.dto.suscripcion.SuscripcionConDetallesDTO;
import com.sd.sistemasd.dto.suscripcion.SuscripcionDTO;
import com.sd.sistemasd.dto.suscripcion.SuscripcionDetalleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SuscripcionServiceImpl implements ISuscripcionService {

    @Autowired
    SuscripcionDAO suscripcionDAO;

    @Autowired
    SuscripcionDetalleDAO suscripcionDetalleDAO;

    @Autowired
    ClienteDAO clienteDAO;

    @Autowired
    DeporteDAO deporteDAO;

    @Override
    @Transactional
    public SuscripcionDTO createSuscripcion(SuscripcionDTO suscripcionDTO) {
        SuscripcionBean suscripcion = new SuscripcionBean();
        suscripcion.setPlanPago(suscripcionDTO.getPlanPago());
        suscripcion.setMontoTotal(suscripcionDTO.getMontoTotal());

        // Establece la relación con el cliente utilizando el clienteID del DTO.
        ClienteBean cliente = clienteDAO.findById(suscripcionDTO.getClienteID()).orElse(null);
        if (cliente != null) {
            suscripcion.setCliente(cliente);
        }

        suscripcion = suscripcionDAO.save(suscripcion);
        return convertToDTO(suscripcion);
    }

    @Override
    @Transactional
    public SuscripcionDetalleDTO createSuscripcionDetalle(SuscripcionDetalleDTO detalleDTO) {
        SuscripcionDetalleBean detalle = new SuscripcionDetalleBean();

        // Establece la relación con la suscripción utilizando el suscripcionID del DTO.
        SuscripcionBean suscripcion = suscripcionDAO.findById(detalleDTO.getSuscripcionID()).orElse(null);
        if (suscripcion != null) {
            detalle.setSuscripcion(suscripcion);
        }

        // Establece la relación con el deporte utilizando el deporteID del DTO.
        DeporteBean deporte = deporteDAO.findById(detalleDTO.getDeporteID()).orElse(null);
        if (deporte != null) {
            detalle.setDeporte(deporte);
        }

        detalle = suscripcionDetalleDAO.save(detalle);
        return convertToDetalleDTO(detalle);
    }

    @Override
    @Cacheable(cacheNames = "sistema-sd", key = "'suscripcion_' + #id")
    @Transactional
    public SuscripcionDTO getSuscripcionById(Long id) {
        SuscripcionBean suscripcion = suscripcionDAO.findById(id).orElse(null);
        if (suscripcion != null) {
            return convertToDTO(suscripcion);
        }
        return null;
    }

    @Override
    @Cacheable(cacheNames = "sistema-sd")
    @Transactional
    public List<SuscripcionDTO> getAllSuscripciones(int page, int size) {
        List<SuscripcionBean> suscripciones = suscripcionDAO.findAll();
        return suscripciones.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    //@Cacheable(cacheNames = "sistema-sd")
    @Transactional
    public List<SuscripcionConDetallesDTO> getAllSuscripcionesWithDetalles(Pageable pageable) {
        Page<SuscripcionBean> suscripcionesPage = suscripcionDAO.findAll(pageable);
        List<SuscripcionConDetallesDTO> suscripcionesConDetalles = new ArrayList<>();

        for (SuscripcionBean suscripcion : suscripcionesPage) {
            List<SuscripcionDetalleDTO> detalles = getSuscripcionDetalles(suscripcion.getSuscripcionID());
            SuscripcionDTO suscripcionDTO = convertToDTO(suscripcion);
            SuscripcionConDetallesDTO suscripcionConDetalles = new SuscripcionConDetallesDTO();
            suscripcionConDetalles.setSuscripcion(suscripcionDTO);
            suscripcionConDetalles.setDetalles(detalles);
            suscripcionesConDetalles.add(suscripcionConDetalles);
        }

        return suscripcionesConDetalles;
    }


    @Override
    @Cacheable(cacheNames = "sistema-sd", key = "'suscripcionDetalle_'+#suscripcionId")
    @Transactional
    public List<SuscripcionDetalleDTO> getSuscripcionDetalles(Long suscripcionId) {
        List<SuscripcionDetalleBean> detalles = suscripcionDetalleDAO.findBySuscripcion_SuscripcionID(suscripcionId);
        // Convierte los detalles de suscripción a DTOs
        return detalles.stream()
                .map(this::convertToDetalleDTO)
                .collect(Collectors.toList());
    }

    @Override
    @CachePut(cacheNames = "sistema-sd", key = "'suscripcion_' + #id")
    @Transactional
    public SuscripcionDTO updateSuscripcion(Long id, SuscripcionDTO suscripcionDTO) {
        SuscripcionBean existingSuscripcion = suscripcionDAO.findById(id).orElse(null);
        if (existingSuscripcion != null) {
            existingSuscripcion.setPlanPago(suscripcionDTO.getPlanPago());
            existingSuscripcion.setMontoTotal(suscripcionDTO.getMontoTotal());
            // Actualiza la relación con el cliente si es necesario.

            existingSuscripcion = suscripcionDAO.save(existingSuscripcion);
            return convertToDTO(existingSuscripcion);
        }
        return null;
    }



    @Override
    @CacheEvict(cacheNames = "sistema-sd", key = "'suscripcion_' + #id")
    @Transactional
    public void deleteSuscripcion(Long id) {
        SuscripcionBean suscripcion = suscripcionDAO.findById(id).orElse(null);
        if (suscripcion != null) {
            // Obtiene los detalles asociados a la suscripción y los elimina
            List<SuscripcionDetalleBean> detalles = suscripcion.getSuscripcionDetalles();
            for (SuscripcionDetalleBean detalle : detalles) {
                suscripcionDetalleDAO.delete(detalle);
            }

            // Luego, elimina la suscripción
            suscripcionDAO.delete(suscripcion);
        }
    }


    @Override
    @CacheEvict(cacheNames = "sistema-sd", key = "'suscripcionDetalle_' + #id")
    @Transactional
    public void deleteSuscripcionDetalle(Long detalleId) {
        suscripcionDetalleDAO.deleteById(detalleId);
    }




    /*
    METODOS PRIVADOS
     */
    private SuscripcionDTO convertToDTO(SuscripcionBean suscripcion) {
        SuscripcionDTO dto = new SuscripcionDTO();
        dto.setSuscripcionID(suscripcion.getSuscripcionID());
        dto.setPlanPago(suscripcion.getPlanPago());
        dto.setMontoTotal(suscripcion.getMontoTotal());
        dto.setEstado(suscripcion.getEstado());

        // Establece el ID del cliente en el DTO
        if (suscripcion.getCliente() != null) {
            dto.setClienteID(suscripcion.getCliente().getClienteID());
        }

        return dto;
    }

    private SuscripcionDetalleDTO convertToDetalleDTO(SuscripcionDetalleBean detalle) {
        SuscripcionDetalleDTO dto = new SuscripcionDetalleDTO();
        dto.setSuscripcionDetalleID(detalle.getSuscripcionDetalleID());

        // Establece el ID de la suscripción y el ID del deporte en el DTO
        if (detalle.getSuscripcion() != null) {
            dto.setSuscripcionID(detalle.getSuscripcion().getSuscripcionID());
        }
        if (detalle.getDeporte() != null) {
            dto.setDeporteID(detalle.getDeporte().getDeporteID());
        }

        return dto;
    }



}