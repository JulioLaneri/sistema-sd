package com.sd.sistemasd.beans.empleado;


import com.sd.sistemasd.beans.asignacion.AsignacionEntrenadorBean;
import com.sd.sistemasd.beans.base.AbstractBean;
import com.sd.sistemasd.beans.facturacion.FacturaEmpleadoBean;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table (name = "empleado")
@Data
public class EmpleadoBean extends AbstractBean {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "empleadoid", nullable = false, unique = true)
    private Long empleadoID;

    @Column(name = "nombre", nullable = true)
    private String nombre;

    @Column(name = "apellido", nullable = true)
    private String apellido;

    @Column(name = "correo", nullable = true)
    private String correoElectronico;

    @Column(name = "telefono", nullable = true)
    private String telefono;

    @Column(name = "horaInicio", nullable = true)
    private String horaInicio;

    @Column(name = "horaFin", nullable = true)
    private String horaFin;

    @Column(name = "tipoEmpleado", nullable = true)
    private String tipoEmpleado;



    // Relación con Facturación-Entrenador
    @OneToMany(mappedBy = "entrenador")
    private List<FacturaEmpleadoBean> facturasEntrenador;

    // Relación con Asignación de Entrenadores por Disciplina
    @OneToMany(mappedBy = "entrenador")
    private List<AsignacionEntrenadorBean> asignacionesEntrenador;
}
