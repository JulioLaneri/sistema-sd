package com.sd.sistemasd.dao.user;

import com.sd.sistemasd.beans.user.UserBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<UserBean, Long> {
    Optional<UserBean> findByEmail(String email);
}
