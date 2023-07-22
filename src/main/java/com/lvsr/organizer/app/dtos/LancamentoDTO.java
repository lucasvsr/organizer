package com.lvsr.organizer.app.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lvsr.organizer.app.enums.TipoLancamentoEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LancamentoDTO {

    private Long id;
    private LocalDate dataLancamento = LocalDate.now();
    @NotNull(message = "É necessário informar a descrição do lançamento")
    private String descricao;
    @Min(value = 1, message = "O valor da transação deve ser de no minímo 1 real.")
    private Double valor;
    @NotNull(message = "É necessário informar o tipo do lançamento: ENTRADA, SAIDA")
    private TipoLancamentoEnum tipo;
    @NotNull(message = "É necessário informar a conta do lançamento")
    private Long contaId;
    @NotNull(message = "É necessário informar o dono da conta do lançamento")
    private Long contaDono;
    private Boolean efetivado = false;

}
