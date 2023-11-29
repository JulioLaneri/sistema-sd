package com.sd.sistemasd.beans.facturacion;


import com.sd.sistemasd.beans.base.AbstractBean;
import com.sd.sistemasd.beans.empleado.EmpleadoBean;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "facturacionEmpleado")
@Data
public class FacturaEmpleadoBean extends AbstractBean {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "facturaEmpleadoid", nullable = false, unique = true)
    private Long facturaEmpleadoID;

    @Column(name = "fechaFacturacion", nullable = true)
    private Date fechaFacturacion;

    @Column(name = "montoTotal", nullable = true)
    private double montoTotal;

    @Column(name = "estadoPago", nullable = true)
    private boolean estadoPago;

    @Column(name = "fechaPago", nullable = true)
    private Date fechaPago;

    // Relación con Empleado
    @ManyToOne
    @JoinColumn(name = "empleadoid")
    private EmpleadoBean entrenador;

}
