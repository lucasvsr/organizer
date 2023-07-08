package com.lvsr.organizer.app.dtos;

import com.lvsr.organizer.app.enums.TipoContaEnum;
import com.lvsr.organizer.app.models.Instituicao;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContaDTO {

    private Long id;
    private String nome;
    private TipoContaEnum tipo;
    private Long saldo;
    private Long donoId;
    private Long instituicaoId;

}
