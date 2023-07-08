package com.lvsr.organizer.app.mappers;

import com.lvsr.organizer.app.dtos.InstituicaoDTO;
import com.lvsr.organizer.app.models.Instituicao;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InstituicaoMapper {

    Instituicao toModel(InstituicaoDTO dto);

    InstituicaoDTO toDto(Instituicao model);

}
