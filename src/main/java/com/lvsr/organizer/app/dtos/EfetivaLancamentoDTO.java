package com.lvsr.organizer.app.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EfetivaLancamentoDTO {

    @NotNull(message = "É necessário informar o dono da conta do lançamento")
    private Long donoId;
    @NotNull(message = "É necessário informar o lançamento")
    private Long lancamentoId;

}
