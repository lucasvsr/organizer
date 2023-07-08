package com.lvsr.organizer.app.mappers;

import com.lvsr.organizer.app.dtos.ContaDTO;
import com.lvsr.organizer.app.models.Conta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UsuarioMapper.class)
public interface ContaMapper {


    @Mapping(source = "donoId", target = "dono.id")
    @Mapping(source = "instituicaoId", target = "instituicao.id")
    Conta toModel(ContaDTO dto);

    @Mapping(source = "dono.id", target = "donoId")
    @Mapping(source = "instituicao.id", target = "instituicaoId")
    ContaDTO toDto(Conta model);

}
