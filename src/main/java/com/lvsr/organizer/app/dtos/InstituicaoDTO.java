package com.lvsr.organizer.app.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InstituicaoDTO {

    private Long id;
    @NotNull(message = "É necessário informar o nome da instituição")
    private String nome;

}
