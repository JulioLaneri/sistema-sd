package com.sd.sistemasd.dto.deporte;

import com.sd.sistemasd.dto.base.BaseDTO;
import lombok.Data;

@Data
public class DeporteDTO extends BaseDTO {
    private Long deporteid;
    private String nombre;
    private String descripcion;
}
