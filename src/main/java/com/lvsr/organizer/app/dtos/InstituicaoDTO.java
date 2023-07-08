package com.lvsr.organizer.app.dtos;

import com.lvsr.organizer.app.models.Conta;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InstituicaoDTO {

    private Long id;
    private String nome;

}
