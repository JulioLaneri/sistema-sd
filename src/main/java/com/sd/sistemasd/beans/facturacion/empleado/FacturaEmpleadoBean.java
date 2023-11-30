package com.sd.sistemasd.beans.facturacion.empleado;


import com.sd.sistemasd.beans.base.AbstractBean;
import com.sd.sistemasd.beans.empleado.EmpleadoBean;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "facturaEmpleado")
@Data
public class FacturaEmpleadoBean extends AbstractBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "facturaid", nullable = false, unique = true)
    private Long facturaid;

    @Column(name = "fecha", nullable = true)
    private Date fecha;

    @Column
    private String nombreEmpleado;

    @Column
    private String rucEmpleado;

    @Column(name = "total", nullable = true)
    private double total;

    // Relación con Empleado
    @ManyToOne
    @JoinColumn(name = "empleadoid")
    private EmpleadoBean empleado;

    @OneToMany(mappedBy = "facturaEmpleado", cascade = CascadeType.ALL, orphanRemoval = true)
    List<FacturaEmpleadoDetalleBean> detalles;

}

