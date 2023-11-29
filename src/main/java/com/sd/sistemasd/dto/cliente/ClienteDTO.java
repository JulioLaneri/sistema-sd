package com.sd.sistemasd.dto.cliente;

import com.sd.sistemasd.dto.base.BaseDTO;
import lombok.Data;

@Data
public class ClienteDTO extends BaseDTO {
    private Long clienteId;
    private String nombre;
    private String cedula;
    private String correoElectronico;
    private String telefono;

}
