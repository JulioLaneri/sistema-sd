package com.sd.sistemasd.beans.role;

import com.sd.sistemasd.beans.base.AbstractBean;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "role")
@Data
public class RoleBean extends AbstractBean {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @Column(unique = true)
    private String name;

    @Column
    private String description;

}
