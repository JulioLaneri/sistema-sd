package com.sd.sistemasd.dao.suscripcion;

import com.sd.sistemasd.beans.suscripcion.SuscripcionDetalleBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuscripcionDetalleDAO extends JpaRepository<SuscripcionDetalleBean, Long> {
    List<SuscripcionDetalleBean> findBySuscripcion_SuscripcionID(Long suscripcionId);
}
