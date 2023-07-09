package com.lvsr.organizer.app.services;

import com.lvsr.organizer.app.dtos.ContaDTO;
import com.lvsr.organizer.app.dtos.LancamentoDTO;
import com.lvsr.organizer.app.enums.TipoLancamentoEnum;
import com.lvsr.organizer.app.exceptions.*;
import com.lvsr.organizer.app.interfaces.IService;
import com.lvsr.organizer.app.mappers.LancamentoMapper;
import com.lvsr.organizer.app.models.Lancamento;
import com.lvsr.organizer.app.repositories.LancamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class LancamentoService implements IService<LancamentoDTO> {

    @Autowired
    private LancamentoRepository repository;

    @Autowired
    private LancamentoMapper mapper;

    @Autowired
    private ContaService contaService;

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public LancamentoDTO salvar(LancamentoDTO lancamentoDTO) throws NegocialException {

        return mapper.toDto(repository.save(mapper.toModel(validar(lancamentoDTO))));

    }

    @Override
    public LancamentoDTO excluir(Long id) throws NegocialException {

        LancamentoDTO dto = recuperar(id);

        operar(dto.getTipo().equals(TipoLancamentoEnum.ENTRADA) ? TipoLancamentoEnum.SAIDA : TipoLancamentoEnum.ENTRADA,
                contaService.recuperar(dto.getContaId()),
                dto.getValor());

        repository.deleteById(dto.getId());

        return dto;

    }

    @Override
    public LancamentoDTO recuperar(Long id) throws NegocialException {
        
        if (id == null || id == 0L) {

            throw new LancamentoNaoEncontradoException();

        }

        return mapper.toDto(repository.findById(id).orElseThrow(LancamentoNaoEncontradoException::new));

    }

    @Override
    public LancamentoDTO validar(LancamentoDTO lancamentoDTO) throws NegocialException {

        Lancamento entidade;
        ContaDTO conta = contaService.recuperar(lancamentoDTO.getContaId());

        conta.setDonoId(lancamentoDTO.getContaDono());

        contaService.validar(conta);

        if (Objects.nonNull(lancamentoDTO.getId())) {

            entidade = mapper.toModel(recuperar(lancamentoDTO.getId()));

            operar(entidade.getTipo().equals(TipoLancamentoEnum.ENTRADA) ? TipoLancamentoEnum.SAIDA : TipoLancamentoEnum.ENTRADA, conta, entidade.getValor());

        }

        operar(lancamentoDTO.getTipo(), conta, lancamentoDTO.getValor());

        return lancamentoDTO;

    }

    public List<LancamentoDTO> recuperarLancamentosUsuario(Long userId) throws NegocialException {

        return repository.findByUserId(usuarioService.recuperar(userId).getId()).stream().map(lancamento -> mapper.toDto(lancamento)).toList();

    }

    public void excluirLancamentosConta(Long conta) {

        for (Lancamento lancamento : repository.findByConta(conta)) {

            repository.deleteById(lancamento.getId());

        }

    }

    private void operar(TipoLancamentoEnum tipo, ContaDTO conta, Double valor) throws NegocialException {

        if (tipo.equals(TipoLancamentoEnum.ENTRADA)) {

            contaService.atualizarSaldo(valor, conta);

        } else {

            contaService.atualizarSaldo(-valor, conta);

        }

    }

}
