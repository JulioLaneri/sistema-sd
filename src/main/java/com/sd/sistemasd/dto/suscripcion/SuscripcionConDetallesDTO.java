package com.sd.sistemasd.dto.suscripcion;

import com.sd.sistemasd.dto.base.BaseDTO;
import lombok.Data;

import java.util.List;

@Data
public class SuscripcionConDetallesDTO extends BaseDTO {
    private SuscripcionDTO suscripcion;
    private List<SuscripcionDetalleDTO> detalles;

}
