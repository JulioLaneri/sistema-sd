package com.sd.sistemasd.dto.suscripcion;

import com.sd.sistemasd.dto.base.BaseDTO;
import lombok.Data;

@Data
public class SuscripcionDTO extends BaseDTO {
    private Long suscripcionID;
    private Long clienteID;
}