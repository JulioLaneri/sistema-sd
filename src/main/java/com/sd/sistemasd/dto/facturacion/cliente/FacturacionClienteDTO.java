package com.sd.sistemasd.dto.facturacion.cliente;

import com.sd.sistemasd.dto.base.BaseDTO;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class FacturacionClienteDTO extends BaseDTO {
    private Long facturaid;
    private Date fecha;
    private double total;
    private String nombreCliente;
    private String rucCliente;
    private Long clienteid;
    private List<FactClienteDetallesDTO> detalles;
}
