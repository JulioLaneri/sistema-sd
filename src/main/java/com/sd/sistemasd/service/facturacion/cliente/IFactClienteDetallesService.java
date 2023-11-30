package com.sd.sistemasd.service.facturacion.cliente;

import com.sd.sistemasd.dto.facturacion.cliente.FactClienteDetallesDTO;

import java.util.List;

public interface IFactClienteDetallesService {
    FactClienteDetallesDTO create(FactClienteDetallesDTO detallesDTO);
    List<FactClienteDetallesDTO> getDetalles(Long id);
}
