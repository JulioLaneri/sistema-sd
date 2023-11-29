package com.sd.sistemasd.service.User;

import com.sd.sistemasd.beans.user.UserBean;
import com.sd.sistemasd.dao.user.UserDao;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Transactional
    public Set<String> getUserRoles(String email) {
        Optional<UserBean> userOptional = userDao.findByEmail(email);
        if (userOptional.isPresent()) {
            UserBean user = userOptional.get();
            return user.getRoles().stream()
                    .map(role -> role.getName())
                    .collect(Collectors.toSet());
        }
        return Collections.emptySet();
    }
}
