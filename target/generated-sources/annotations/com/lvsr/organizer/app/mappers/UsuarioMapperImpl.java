package com.lvsr.organizer.app.mappers;

import com.lvsr.organizer.app.dtos.ContaDTO;
import com.lvsr.organizer.app.dtos.UsuarioDTO;
import com.lvsr.organizer.app.models.Conta;
import com.lvsr.organizer.app.models.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-09T07:27:04-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Eclipse Adoptium)"
)
@Component
public class UsuarioMapperImpl implements UsuarioMapper {

    @Autowired
    private ContaMapper contaMapper;

    @Override
    public Usuario toModel(UsuarioDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Usuario usuario = new Usuario();

        usuario.setId( dto.getId() );
        usuario.setNome( dto.getNome() );
        usuario.setEmail( dto.getEmail() );
        usuario.setSenha( dto.getSenha() );
        usuario.setContas( contaDTOListToContaList( dto.getContas() ) );

        return usuario;
    }

    @Override
    public UsuarioDTO toDto(Usuario model) {
        if ( model == null ) {
            return null;
        }

        UsuarioDTO usuarioDTO = new UsuarioDTO();

        usuarioDTO.setId( model.getId() );
        usuarioDTO.setNome( model.getNome() );
        usuarioDTO.setEmail( model.getEmail() );
        usuarioDTO.setSenha( model.getSenha() );
        usuarioDTO.setContas( contaListToContaDTOList( model.getContas() ) );

        return usuarioDTO;
    }

    protected List<Conta> contaDTOListToContaList(List<ContaDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<Conta> list1 = new ArrayList<Conta>( list.size() );
        for ( ContaDTO contaDTO : list ) {
            list1.add( contaMapper.toModel( contaDTO ) );
        }

        return list1;
    }

    protected List<ContaDTO> contaListToContaDTOList(List<Conta> list) {
        if ( list == null ) {
            return null;
        }

        List<ContaDTO> list1 = new ArrayList<ContaDTO>( list.size() );
        for ( Conta conta : list ) {
            list1.add( contaMapper.toDto( conta ) );
        }

        return list1;
    }
}
