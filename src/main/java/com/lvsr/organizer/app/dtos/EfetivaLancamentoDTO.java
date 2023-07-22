package com.lvsr.organizer.app.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EfetivaLancamentoDTO {

    private Long donoId;
    private Long lancamentoId;

}
