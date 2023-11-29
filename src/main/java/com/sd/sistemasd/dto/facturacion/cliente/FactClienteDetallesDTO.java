package com.sd.sistemasd.dto.facturacion.cliente;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class FactClienteDetallesDTO {
    private Long detalleid;
    private double precio;
    private double subtotal;
    private Long suscripcionid;
    private Long facturaid;
}
