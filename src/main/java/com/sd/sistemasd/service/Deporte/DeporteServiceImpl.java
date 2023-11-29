package com.sd.sistemasd.service.Deporte;


import com.sd.sistemasd.beans.deporte.DeporteBean;
import com.sd.sistemasd.dao.deporte.DeporteDAO;
import com.sd.sistemasd.dto.deporte.DeporteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeporteServiceImpl implements IDeporteService {

    @Autowired
    DeporteDAO deporteDAO;
    @Override
    @Transactional
    public DeporteDTO createDeporte(DeporteDTO deporteDTO) {
        DeporteBean deporte = new DeporteBean();
        deporte.setNombre(deporteDTO.getNombre());
        deporte.setDescripcion(deporte.getDescripcion());
        deporte = deporteDAO.save(deporte);
        return convertToDTO(deporte);
    }

    @Override
    @Cacheable(cacheNames = "sistema-sd", key = "'deporte_' + #id" )
    @Transactional
    public DeporteDTO getDeporteById(Long id) {
        DeporteBean deporte = deporteDAO.findById(id).orElse(null);
        if(deporte != null){
            return convertToDTO(deporte);
        }
        return null;
    }

    @Override
    @Cacheable(cacheNames = "sistema-sd")
    @Transactional
    public Page<DeporteDTO> getAllDeportes(Pageable pageable) {
        Page<DeporteBean> deportesPage = deporteDAO.findAll(pageable);
        return deportesPage.map(this::convertToDTO);
    }


    @Override
    @CachePut(cacheNames = "sistema-sd", key = "'deporte_' + #id" )
    @Transactional
    public DeporteDTO updateDeporte(Long id, DeporteDTO deporteDTO) {
        DeporteBean existingDeporte = deporteDAO.findById(id).orElse(null);
        if(existingDeporte != null){
            existingDeporte.setNombre(deporteDTO.getNombre());
            existingDeporte.setDescripcion(deporteDTO.getDescripcion());

            existingDeporte = deporteDAO.save(existingDeporte);
            return convertToDTO(existingDeporte);
        }
        return null;
    }

    @Override
    @CacheEvict(cacheNames = "sistema-sd", key = "'deporte_' + #id" )
    @Transactional
    public void deleteDeporte(Long id) {
        deporteDAO.deleteById(id);
    }

    private DeporteDTO convertToDTO(DeporteBean deporte) {
        DeporteDTO dto = new DeporteDTO();
        dto.setDeporteid(deporte.getDeporteID());
        dto.setNombre(deporte.getNombre());
        dto.setDescripcion(deporte.getDescripcion());
        return dto;
    }
}
