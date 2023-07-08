package com.lvsr.organizer.app.mappers;

import com.lvsr.organizer.app.dtos.InstituicaoDTO;
import com.lvsr.organizer.app.models.Instituicao;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-08T17:57:03-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Eclipse Adoptium)"
)
@Component
public class InstituicaoMapperImpl implements InstituicaoMapper {

    @Override
    public Instituicao toModel(InstituicaoDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Instituicao instituicao = new Instituicao();

        instituicao.setId( dto.getId() );
        instituicao.setNome( dto.getNome() );

        return instituicao;
    }

    @Override
    public InstituicaoDTO toDto(Instituicao model) {
        if ( model == null ) {
            return null;
        }

        InstituicaoDTO instituicaoDTO = new InstituicaoDTO();

        instituicaoDTO.setId( model.getId() );
        instituicaoDTO.setNome( model.getNome() );

        return instituicaoDTO;
    }
}
