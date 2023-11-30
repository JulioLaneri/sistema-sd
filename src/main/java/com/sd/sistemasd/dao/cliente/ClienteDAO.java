package com.sd.sistemasd.dao.cliente;

import com.sd.sistemasd.beans.cliente.ClienteBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteDAO extends JpaRepository<ClienteBean,Long> {
    Page<ClienteBean> findAll(Pageable pageable);
}