package com.sd.sistemasd.dao.facturacionCliente;

import com.sd.sistemasd.beans.facturacion.cliente.FacturaClienteBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface FacturacionClienteDAO extends JpaRepository<FacturaClienteBean, Long> {
    @Query("SELECT DISTINCT fc FROM FacturaClienteBean fc LEFT JOIN FETCH fc.detalles")
    Page<FacturaClienteBean> findAllWithDetalles(Pageable pageable);
}

