package com.lvsr.organizer.app.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstituicaoDTO {

    private Long id;
    @NotNull(message = "É necessário informar o nome da instituição")
    private String nome;

}
