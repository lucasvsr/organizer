package com.lvsr.organizer.app.mappers;

import com.lvsr.organizer.app.dtos.LancamentoDTO;
import com.lvsr.organizer.app.models.Lancamento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LancamentoMapper {

    @Mapping(source = "contaId", target = "conta.id")
    Lancamento toModel(LancamentoDTO dto);

    @Mapping(source = "conta.id", target = "contaId")
    LancamentoDTO toDto(Lancamento model);

}
