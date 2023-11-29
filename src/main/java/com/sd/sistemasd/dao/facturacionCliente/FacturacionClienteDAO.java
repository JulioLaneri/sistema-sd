package com.sd.sistemasd.dao.facturacionCliente;

import com.sd.sistemasd.beans.facturacion.cliente.FacturaClienteBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacturacionClienteDAO extends JpaRepository<FacturaClienteBean, Long> {
    Page<FacturaClienteBean> findAll(Pageable pageable);
}
