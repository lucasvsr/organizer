package com.lvsr.organizer.app.mappers;

import com.lvsr.organizer.app.dtos.ContaDTO;
import com.lvsr.organizer.app.models.Conta;
import com.lvsr.organizer.app.models.Instituicao;
import com.lvsr.organizer.app.models.Usuario;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-08T17:57:02-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Eclipse Adoptium)"
)
@Component
public class ContaMapperImpl implements ContaMapper {

    @Override
    public Conta toModel(ContaDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Conta conta = new Conta();

        conta.setDono( contaDTOToUsuario( dto ) );
        conta.setInstituicao( contaDTOToInstituicao( dto ) );
        conta.setId( dto.getId() );
        conta.setNome( dto.getNome() );
        conta.setTipo( dto.getTipo() );
        conta.setSaldo( dto.getSaldo() );

        return conta;
    }

    @Override
    public ContaDTO toDto(Conta model) {
        if ( model == null ) {
            return null;
        }

        ContaDTO contaDTO = new ContaDTO();

        contaDTO.setDonoId( modelDonoId( model ) );
        contaDTO.setInstituicaoId( modelInstituicaoId( model ) );
        contaDTO.setId( model.getId() );
        contaDTO.setNome( model.getNome() );
        contaDTO.setTipo( model.getTipo() );
        contaDTO.setSaldo( model.getSaldo() );

        return contaDTO;
    }

    protected Usuario contaDTOToUsuario(ContaDTO contaDTO) {
        if ( contaDTO == null ) {
            return null;
        }

        Usuario usuario = new Usuario();

        usuario.setId( contaDTO.getDonoId() );

        return usuario;
    }

    protected Instituicao contaDTOToInstituicao(ContaDTO contaDTO) {
        if ( contaDTO == null ) {
            return null;
        }

        Instituicao instituicao = new Instituicao();

        instituicao.setId( contaDTO.getInstituicaoId() );

        return instituicao;
    }

    private Long modelDonoId(Conta conta) {
        if ( conta == null ) {
            return null;
        }
        Usuario dono = conta.getDono();
        if ( dono == null ) {
            return null;
        }
        Long id = dono.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long modelInstituicaoId(Conta conta) {
        if ( conta == null ) {
            return null;
        }
        Instituicao instituicao = conta.getInstituicao();
        if ( instituicao == null ) {
            return null;
        }
        Long id = instituicao.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
