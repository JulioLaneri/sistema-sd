package com.sd.sistemasd.dao.deporte;

import com.sd.sistemasd.beans.deporte.DeporteBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeporteDAO extends JpaRepository<DeporteBean,Long> {
    Page<DeporteBean> findAll(Pageable pageable);
}
