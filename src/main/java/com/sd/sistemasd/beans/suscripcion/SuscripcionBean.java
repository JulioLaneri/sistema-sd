package com.sd.sistemasd.beans.suscripcion;


import com.sd.sistemasd.beans.base.AbstractBean;
import com.sd.sistemasd.beans.cliente.ClienteBean;
import com.sd.sistemasd.beans.facturacion.cliente.FacturacionClienteDetalles;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "suscripcion")
@Data
public class SuscripcionBean extends AbstractBean {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "suscripcionid", nullable = false, unique = true)
    private Long suscripcionID;

    // Relación con Cliente
    @ManyToOne
    @JoinColumn(name = "clienteid")
    private ClienteBean cliente;

    // Relación con Detalle Suscripción
    @OneToMany(mappedBy = "suscripcion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SuscripcionDetalleBean> suscripcionDetalles;

    @OneToMany(mappedBy = "suscripcion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FacturacionClienteDetalles> facturacionClienteDetalles;
}
