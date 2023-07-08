package com.lvsr.organizer.app.dtos;

import com.lvsr.organizer.app.enums.TipoLancamentoEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class LancamentoDTO {

    private Long id;

    private LocalDate dataLancamento;

    private String descricao;

    private Long valor;

    private TipoLancamentoEnum tipo;

    private Long contaId;

}
