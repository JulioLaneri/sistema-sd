package com.sd.sistemasd.beans.suscripcion;


import com.sd.sistemasd.beans.base.AbstractBean;
import com.sd.sistemasd.beans.deporte.DeporteBean;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "suscripcionDetalle")
@Data
public class SuscripcionDetalleBean extends AbstractBean {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long suscripcionDetalleID;

    @Column(name = "planPago", nullable = true)
    private String planPago;

    @Column(name = "costo", nullable = true)
    private double costo;

    @Column(name = "estado", nullable = true)
    private String estado;

    // Relación con Suscripciones como Cabecera-Detalle
    @ManyToOne
    @JoinColumn(name = "suscripcionid")
    private SuscripcionBean suscripcion;

    // Relación con Deporte
    @ManyToOne
    @JoinColumn(name = "deporteid")
    private DeporteBean deporte;
}
