package com.sd.sistemasd.dao.role;

import com.sd.sistemasd.beans.role.RoleBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleDao extends JpaRepository<RoleBean, Long> {
    Optional<RoleBean> findByName(String name);
}
