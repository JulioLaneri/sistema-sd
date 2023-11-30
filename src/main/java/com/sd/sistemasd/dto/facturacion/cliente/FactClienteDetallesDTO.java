package com.sd.sistemasd.dto.facturacion.cliente;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sd.sistemasd.dto.base.BaseDTO;
import lombok.Data;

@Data
public class FactClienteDetallesDTO extends BaseDTO {
    private Long detalleid;
    private double precio;
    private double subtotal;
    private Long suscripcionid;
    private Long facturaid;
}
