package com.sd.sistemasd.dao.suscripcion;


import com.sd.sistemasd.beans.suscripcion.SuscripcionBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface SuscripcionDAO extends JpaRepository<SuscripcionBean, Long> {
    Page<SuscripcionBean> findAll(Pageable pageable);


}
