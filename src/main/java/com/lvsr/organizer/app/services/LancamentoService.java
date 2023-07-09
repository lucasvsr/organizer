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

        if (Objects.nonNull(dto)) {

            operar(dto.getTipo().equals(TipoLancamentoEnum.ENTRADA) ? TipoLancamentoEnum.SAIDA : TipoLancamentoEnum.ENTRADA,
                    contaService.recuperar(dto.getContaId()),
                    dto.getValor());

            repository.deleteById(dto.getId());

            return dto;

        } else {

            throw new LancamentoNaoEncontradoException();

        }

    }

    @Override
    public LancamentoDTO recuperar(Long id) throws NegocialException {
        return id == null || id == 0L ? null : mapper.toDto(repository.findById(id).orElseThrow(LancamentoNaoEncontradoException::new));
    }

    @Override
    public LancamentoDTO validar(LancamentoDTO lancamentoDTO) throws NegocialException {

        boolean temId = Objects.nonNull(lancamentoDTO.getId());
        Lancamento entidade = temId ? repository.findById(lancamentoDTO.getId()).orElse(null) : null;
        ContaDTO conta = contaService.recuperar(lancamentoDTO.getContaId());

        if (Objects.isNull(lancamentoDTO.getTipo())) {

            throw new LancamentoNaoTipadoException();

        }

        if (Objects.isNull(conta)) {

            throw new ContaInexistenteException();

        }

        if (temId) {

            if (Objects.isNull(entidade)) {

                throw new LancamentoNaoEncontradoException();

            }

            operar(entidade.getTipo().equals(TipoLancamentoEnum.ENTRADA) ? TipoLancamentoEnum.SAIDA : TipoLancamentoEnum.ENTRADA, conta, entidade.getValor());

        } else {

            if (Objects.isNull(lancamentoDTO.getDataLancamento())) {

                throw new LancamentoNaoDatadoException();

            }

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

    private void operar(TipoLancamentoEnum tipo, ContaDTO conta, Long valor) throws NegocialException {

        if (tipo.equals(TipoLancamentoEnum.ENTRADA)) {

            contaService.atualizarSaldo(valor, conta);

        } else {

            contaService.atualizarSaldo(-valor, conta);

        }

    }

}
