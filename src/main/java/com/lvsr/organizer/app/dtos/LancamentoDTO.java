package com.lvsr.organizer.app.dtos;

import com.lvsr.organizer.app.enums.TipoLancamentoEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class LancamentoDTO {

    private Long id;
    @NotNull(message = "É necessário informar a data do lançamento")
    private LocalDate dataLancamento;
    @NotNull(message = "É necessário informar a descrição do lançamento")
    private String descricao;
    @Min(value = 1, message = "O valor da transação deve ser de no minímo 1 real.")
    private Long valor;
    @NotNull(message = "É necessário informar o tipo do lançamento: ENTRADA, SAIDA")
    private TipoLancamentoEnum tipo;
    @NotNull(message = "É necessário informar a conta do lançamento")
    private Long contaId;

}
