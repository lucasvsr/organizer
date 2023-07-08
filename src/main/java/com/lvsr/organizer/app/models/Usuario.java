package com.lvsr.organizer.app.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(nullable = false, unique = true, length = 60)
    private String email;

    @Column(nullable = false)
    private String senha;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dono", cascade = CascadeType.REFRESH)
    private List<Conta> contas;

}
