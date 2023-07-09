package com.lvsr.organizer.app.dtos;

import com.lvsr.organizer.app.enums.TipoContaEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContaDTO {

    private Long id;
    private String nome;
    @NotNull(message = "É necessário informar o tipo da conta: BANCARIA, CREDITO, INVESTIMENTO")
    private TipoContaEnum tipo;
    @Min(value = 0, message = "O saldo precisa ser maior que zero")
    private Long saldo;
    @NotNull(message = "É necessário informar o dono da conta")
    private Long donoId;
    @NotNull(message = "É necessário informar a instituição da conta")
    private Long instituicaoId;

}
