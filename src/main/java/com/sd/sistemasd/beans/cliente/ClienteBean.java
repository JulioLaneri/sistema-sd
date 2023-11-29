package com.sd.sistemasd.beans.cliente;


import com.sd.sistemasd.beans.base.AbstractBean;
import com.sd.sistemasd.beans.facturacion.cliente.FacturaClienteBean;
import com.sd.sistemasd.beans.suscripcion.SuscripcionBean;
import jakarta.persistence.*;
import lombok.Data;


import java.util.Set;


@Entity
@Table (name = "cliente")
@Data
public class ClienteBean extends AbstractBean {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clienteid", nullable = false, unique = true)
    private Long clienteID;

    @Column(name = "nombre", nullable = true)
    private String nombre;

    @Column(name = "cedula", nullable = true, unique = true)
    private String cedula;

    @Column(name = "correo", nullable = true)
    private String correoElectronico;

    @Column(name = "telefono", nullable = true)
    private String telefono;

    @Column
    private String ruc;

    // Relación con Suscripciones
    @OneToMany(mappedBy = "cliente")
    private Set<SuscripcionBean> suscripciones;



    // Relación con Facturación-Cliente
    @OneToMany(mappedBy = "cliente")
    private Set<FacturaClienteBean> facturasCliente;

}
