package com.sd.sistemasd.beans.facturacion.empleado;

import com.sd.sistemasd.beans.base.AbstractBean;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class FacturaEmpleadoDetalleBean extends AbstractBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detalleid", nullable = false, unique = true)
    private Long id;

    @Column
    private double subtotal;

    @ManyToOne
    @JoinColumn(name = "facturaid")
    FacturaEmpleadoBean facturaEmpleado;

}