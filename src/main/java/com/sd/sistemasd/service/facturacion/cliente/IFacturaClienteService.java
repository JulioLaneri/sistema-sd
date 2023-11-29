package com.sd.sistemasd.service.facturacion.cliente;

import com.sd.sistemasd.dto.facturacion.cliente.FacturacionClienteDTO;

import java.util.List;


public interface IFacturaClienteService {

    FacturacionClienteDTO create(FacturacionClienteDTO facturacionClienteDTO);
    FacturacionClienteDTO getById(Long id);
    List<FacturacionClienteDTO> getAll(int page, int size);
    void delete(Long id);

}
