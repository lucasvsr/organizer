package com.lvsr.organizer.app.mappers;

import com.lvsr.organizer.app.dtos.LancamentoDTO;
import com.lvsr.organizer.app.models.Conta;
import com.lvsr.organizer.app.models.Lancamento;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-08T17:57:03-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Eclipse Adoptium)"
)
@Component
public class LancamentoMapperImpl implements LancamentoMapper {

    @Override
    public Lancamento toModel(LancamentoDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Lancamento lancamento = new Lancamento();

        lancamento.setConta( lancamentoDTOToConta( dto ) );
        lancamento.setId( dto.getId() );
        lancamento.setDataLancamento( dto.getDataLancamento() );
        lancamento.setDescricao( dto.getDescricao() );
        lancamento.setValor( dto.getValor() );
        lancamento.setTipo( dto.getTipo() );

        return lancamento;
    }

    @Override
    public LancamentoDTO toDto(Lancamento model) {
        if ( model == null ) {
            return null;
        }

        LancamentoDTO lancamentoDTO = new LancamentoDTO();

        lancamentoDTO.setContaId( modelContaId( model ) );
        lancamentoDTO.setId( model.getId() );
        lancamentoDTO.setDataLancamento( model.getDataLancamento() );
        lancamentoDTO.setDescricao( model.getDescricao() );
        lancamentoDTO.setValor( model.getValor() );
        lancamentoDTO.setTipo( model.getTipo() );

        return lancamentoDTO;
    }

    protected Conta lancamentoDTOToConta(LancamentoDTO lancamentoDTO) {
        if ( lancamentoDTO == null ) {
            return null;
        }

        Conta conta = new Conta();

        conta.setId( lancamentoDTO.getContaId() );

        return conta;
    }

    private Long modelContaId(Lancamento lancamento) {
        if ( lancamento == null ) {
            return null;
        }
        Conta conta = lancamento.getConta();
        if ( conta == null ) {
            return null;
        }
        Long id = conta.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
