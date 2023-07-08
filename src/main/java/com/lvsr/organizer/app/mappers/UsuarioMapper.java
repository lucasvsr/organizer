package com.lvsr.organizer.app.mappers;

import com.lvsr.organizer.app.dtos.UsuarioDTO;
import com.lvsr.organizer.app.models.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ContaMapper.class)
public interface UsuarioMapper {

    Usuario toModel(UsuarioDTO dto);

    UsuarioDTO toDto(Usuario model);

}
