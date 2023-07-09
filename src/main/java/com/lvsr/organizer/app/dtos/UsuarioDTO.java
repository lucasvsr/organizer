package com.lvsr.organizer.app.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UsuarioDTO {

    private Long id;
    @NotNull(message = "É necessário informar um nome")
    private String nome;
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "É necessário informar um e-mail válido")
    private String email;
    @Size(min = 6, message = "A senha precisa ter no minímo 6 caracteres")
    private String senha;
    private List<ContaDTO> contas;

}
