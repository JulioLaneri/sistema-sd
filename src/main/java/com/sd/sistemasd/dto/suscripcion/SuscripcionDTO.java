package com.sd.sistemasd.dto.suscripcion;

import com.sd.sistemasd.dto.base.BaseDTO;
import lombok.Data;

@Data
public class SuscripcionDTO extends BaseDTO {
    private Long suscripcionID;
    private String planPago;
    private double montoTotal;
    private String estado;
    private Long clienteID;
}