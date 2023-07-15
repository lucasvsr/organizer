package com.lvsr.organizer.app.services;

import com.lvsr.organizer.app.dtos.ContaDTO;
import com.lvsr.organizer.app.dtos.LancamentoDTO;
import com.lvsr.organizer.app.enums.TipoLancamentoEnum;
import com.lvsr.organizer.app.exceptions.LancamentoNaoEncontradoException;
import com.lvsr.organizer.app.exceptions.NegocialException;
import com.lvsr.organizer.app.interfaces.IService;
import com.lvsr.organizer.app.mappers.LancamentoMapper;
import com.lvsr.organizer.app.models.Lancamento;
import com.lvsr.organizer.app.repositories.LancamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class LancamentoService implements IService<LancamentoDTO> {

    private final LancamentoRepository repository;

    private final LancamentoMapper mapper;

    private final ContaService contaService;

    private final UsuarioService usuarioService;

    public LancamentoService(LancamentoRepository repository, LancamentoMapper mapper, ContaService contaService, UsuarioService usuarioService) {
        this.repository = repository;
        this.mapper = mapper;
        this.contaService = contaService;
        this.usuarioService = usuarioService;
    }

    @Override
    public LancamentoDTO salvar(LancamentoDTO lancamentoDTO) throws NegocialException {

        Lancamento lancamento = mapper.toModel(validar(lancamentoDTO));
        ContaDTO conta = contaService.recuperar(lancamento.getConta().getId());

        if (Objects.nonNull(lancamento.getId())) {

            operar(lancamento.getTipo().equals(TipoLancamentoEnum.ENTRADA) ? TipoLancamentoEnum.SAIDA : TipoLancamentoEnum.ENTRADA, conta, lancamento.getValor());

        }

        lancamentoDTO = mapper.toDto(repository.save(lancamento));

        operar(lancamentoDTO.getTipo(), conta, lancamentoDTO.getValor());

        return lancamentoDTO;

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

        ContaDTO conta = contaService.recuperar(lancamentoDTO.getContaId());

        conta.setDonoId(lancamentoDTO.getContaDono());

        contaService.validar(conta);

        return lancamentoDTO;

    }

    public List<LancamentoDTO> recuperarLancamentosUsuario(Long userId) throws NegocialException {

        return repository.findByDonoId(usuarioService.recuperar(userId).getId()).stream().map(mapper::toDto).toList();

    }

    public void excluirLancamentosConta(Long conta) {

        for (Lancamento lancamento : repository.findByContaId(conta)) {

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
