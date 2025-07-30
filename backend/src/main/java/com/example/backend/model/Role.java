package com.example.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tbl_role", uniqueConstraints = {
        @UniqueConstraint(name = "role_name", columnNames = {"role_name"})
})
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @NotNull
    @Column(name = "role_name", nullable = false, length = 50)
    private String roleName;

    @JsonIgnore
    @OneToMany(mappedBy = "role",cascade = CascadeType.ALL)
    private Set<UserRole> tblUserRoles = new LinkedHashSet<>();
}