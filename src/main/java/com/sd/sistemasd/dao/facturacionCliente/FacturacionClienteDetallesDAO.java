package com.sd.sistemasd.dao.facturacionCliente;

import com.sd.sistemasd.beans.facturacion.cliente.FacturacionClienteDetalles;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface FacturacionClienteDetallesDAO extends JpaRepository<FacturacionClienteDetalles, Long>{
    List<FacturacionClienteDetalles> findByFacturaCliente_Facturaid(Long id);
}
