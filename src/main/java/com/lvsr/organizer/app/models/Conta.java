package com.lvsr.organizer.app.models;

import com.lvsr.organizer.app.enums.TipoContaEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity(name = "contas")
public class Conta {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoContaEnum tipo;

    @Column(nullable = false)
    private Long saldo;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private Usuario dono;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "conta")
    private List<Lancamento> lancamentos;

    @ManyToOne(fetch = FetchType.EAGER)
    private Instituicao instituicao;

}
