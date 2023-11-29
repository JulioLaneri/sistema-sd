package com.sd.sistemasd.beans.facturacion.cliente;

import com.sd.sistemasd.beans.base.AbstractBean;
import com.sd.sistemasd.beans.suscripcion.SuscripcionBean;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class FacturacionClienteDetalles extends AbstractBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detalleid", nullable = false, unique = true)
    private Long detalleid;

    @Column
    private double precio;

    @Column
    private double subtotal;

    @ManyToOne
    @JoinColumn(name = "suscripcionid")
    private SuscripcionBean suscripcion;


    @ManyToOne
    @JoinColumn(name = "facturaid")
    private FacturaClienteBean facturaCliente;
}
