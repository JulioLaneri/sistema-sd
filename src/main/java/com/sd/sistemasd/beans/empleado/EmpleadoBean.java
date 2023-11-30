package com.sd.sistemasd.beans.empleado;


import com.sd.sistemasd.beans.base.AbstractBean;
import com.sd.sistemasd.beans.facturacion.empleado.FacturaEmpleadoBean;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import com.sd.sistemasd.beans.role.RoleBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.sd.sistemasd.beans.deporte.DeporteBean;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table (name = "empleado")
@Data
public class EmpleadoBean extends AbstractBean implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "empleadoid", nullable = false, unique = true)
    private Long empleadoID;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "password", nullable = false, length = 64)
    private String password;
    @Column(name = "email", nullable = false, length = 50, unique = true)
    private String email;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "cedula")
    private String cedula;

    @Column(name = "rucEmpleado")
    private String rucEmpleado;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleBean> roles;

    @ManyToOne
    @JoinColumn(name = "deporte_id") // Nombre de la columna que almacena el ID del deporte en la tabla de Empleados
    private DeporteBean deporte;

    // Relación con Facturación-Entrenador
    @OneToMany(mappedBy = "empleado")
    private List<FacturaEmpleadoBean> facturasEmpleado;



    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}