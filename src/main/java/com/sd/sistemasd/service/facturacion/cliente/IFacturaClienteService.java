package com.sd.sistemasd.service.facturacion.cliente;

import com.sd.sistemasd.dto.facturacion.cliente.FacturacionClienteDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface IFacturaClienteService {

    FacturacionClienteDTO create(FacturacionClienteDTO facturacionClienteDTO);
    FacturacionClienteDTO getById(Long id);
    Page<FacturacionClienteDTO> getAll(Pageable pageable);
    void delete(Long id);

}
