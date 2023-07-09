package com.lvsr.organizer.app.models;

import com.lvsr.organizer.app.enums.TipoLancamentoEnum;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;

@Data
@Entity(name = "lancamentos")
public class Lancamento {

    @Id
    @GeneratedValue
    private Long id;

    @Temporal(TemporalType.DATE)
    @UpdateTimestamp
    private LocalDate dataLancamento;

    @Column(nullable = false, length = 120)
    private String descricao;

    @Column(nullable = false)
    private Double valor;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoLancamentoEnum tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    private Conta conta;

}
