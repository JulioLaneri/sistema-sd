package com.sd.sistemasd.beans.suscripcion;


import com.sd.sistemasd.beans.base.AbstractBean;
import com.sd.sistemasd.beans.deporte.DeporteBean;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "suscripcionDetalle")
@Data
public class SuscripcionDetalleBean extends AbstractBean {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long suscripcionDetalleID;

    // Relación con Suscripciones como Cabecera-Detalle
    @ManyToOne
    @JoinColumn(name = "suscripcionid")
    private SuscripcionBean suscripcion;

    // Relación con Deporte
    @ManyToOne
    @JoinColumn(name = "deporteid")
    private DeporteBean deporte;
}
