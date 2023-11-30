package com.sd.sistemasd.beans.facturacion.cliente;


import com.sd.sistemasd.beans.base.AbstractBean;
import com.sd.sistemasd.beans.cliente.ClienteBean;
import jakarta.persistence.*;
import lombok.Data;


import java.util.Date;
import java.util.List;

@Entity
@Table(name = "facturacionCliente")
@Data
public class FacturaClienteBean extends AbstractBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "facturaid", nullable = false, unique = true)
    private Long facturaid;

    @Column(name = "fecha", nullable = true)
    private Date fecha;

    @Column(name = "total", nullable = true)
    private double total;

    @Column
    private String nombreCliente;

    @Column
    private String rucCliente;

    @ManyToOne
    @JoinColumn(name = "clienteid")
    private ClienteBean cliente;

    @OneToMany(mappedBy = "facturaCliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FacturacionClienteDetalles> detalles;
}
