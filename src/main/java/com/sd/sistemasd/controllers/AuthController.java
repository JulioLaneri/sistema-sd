package com.sd.sistemasd.controllers;

import com.sd.sistemasd.beans.role.RoleBean;
import com.sd.sistemasd.beans.user.UserBean;
import com.sd.sistemasd.dao.role.RoleDao;
import com.sd.sistemasd.dao.user.UserDao;
import com.sd.sistemasd.security.AuthRequest;
import com.sd.sistemasd.security.AuthResponse;
import com.sd.sistemasd.security.jwt.JwtTokenUtil;
import com.sd.sistemasd.service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authManager;
    @Autowired
    JwtTokenUtil jwtUtil;
    @Autowired
    UserDao userDao;
    @Autowired
    RoleDao roleDao;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request) {
        try {
            final Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            final UserBean user = (UserBean) authentication.getPrincipal();
            final String accessToken = jwtUtil.generateAccesToken(user);

            Set<String> roles = userService.getUserRoles(user.getEmail());

            final AuthResponse response = new AuthResponse(user.getEmail(), accessToken, roles);
            return ResponseEntity.ok().body(response);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid AuthRequest request) {
        // Verificar si el usuario ya existe
        if (userDao.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El usuario ya existe");
        }

        UserBean newUser = new UserBean();
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));

        Set<RoleBean> roles = new HashSet<>();

        String roleName = request.getRole();
        if (roleName != null) {
            RoleBean role = roleDao.findByName(roleName).orElseThrow(() -> new RuntimeException("Role not found"));
            roles.add(role);
        } else {

            roles.add(roleDao.findByName("ROLE_EMPLEADO").orElseThrow(() -> new RuntimeException("Role not found")));
        }

        newUser.setRoles(roles);

        userDao.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado exitosamente");
    }
}
