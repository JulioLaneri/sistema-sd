package com.sd.sistemasd.beans.asignacion;


import com.sd.sistemasd.beans.base.AbstractBean;
import com.sd.sistemasd.beans.deporte.DeporteBean;
import com.sd.sistemasd.beans.empleado.EmpleadoBean;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "asignacionEntrenador")
@Data
public class AsignacionEntrenadorBean extends AbstractBean {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "asignacionid", nullable = false, unique = true)
    private Long asignacionID;

    // Relación con Deporte
    @ManyToOne
    @JoinColumn(name = "deporteid")
    private DeporteBean deporte;

    // Relación con Empleado (Entrenador)
    @ManyToOne
    @JoinColumn(name = "empleadoid")
    private EmpleadoBean entrenador;
}