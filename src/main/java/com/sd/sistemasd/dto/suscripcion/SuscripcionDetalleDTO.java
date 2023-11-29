package com.sd.sistemasd.dto.suscripcion;

import com.sd.sistemasd.dto.base.BaseDTO;
import lombok.Data;

@Data
public class SuscripcionDetalleDTO extends BaseDTO {
    private Long suscripcionDetalleID;
    private Long suscripcionID;
    private Long deporteID;
}