package com.lvsr.organizer.app.services;

import com.lvsr.organizer.app.dtos.ContaDTO;
import com.lvsr.organizer.app.dtos.EfetivaLancamentoDTO;
import com.lvsr.organizer.app.dtos.LancamentoDTO;
import com.lvsr.organizer.app.enums.TipoLancamentoEnum;
import com.lvsr.organizer.app.exceptions.*;
import com.lvsr.organizer.app.interfaces.IService;
import com.lvsr.organizer.app.mappers.LancamentoMapper;
import com.lvsr.organizer.app.models.Lancamento;
import com.lvsr.organizer.app.repositories.LancamentoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class LancamentoService implements IService<LancamentoDTO, LancamentoRepository, LancamentoMapper> {

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

        Lancamento lancamento = Objects.nonNull(lancamentoDTO.getId()) ? mapper.toModel(recuperar(lancamentoDTO.getId())) : null;
        lancamentoDTO = validar(lancamentoDTO);

        if (Objects.nonNull(lancamento) && lancamento.getEfetivado()) {

            compararOperar(lancamentoDTO, lancamento);
            lancamentoDTO.setEfetivado(lancamento.getEfetivado());

        }

        if (lancamentoDTO.getEfetivado() && (Objects.nonNull(lancamento) && !lancamento.getEfetivado() || Objects.isNull(lancamento))) {

            operar(lancamentoDTO.getTipo(), lancamentoDTO.getContaId(), lancamentoDTO.getValor());

        }

        return mapper.toDto(repository.save(mapper.toModel(lancamentoDTO)));

    }

    @Override
    public LancamentoDTO excluir(Long id) throws NegocialException {

        LancamentoDTO lancamento = recuperar(id);

        if (lancamento.getEfetivado()) {

            operar(lancamento.getTipo().equals(TipoLancamentoEnum.ENTRADA) ? TipoLancamentoEnum.SAIDA : TipoLancamentoEnum.ENTRADA,
                    lancamento.getContaId(),
                    lancamento.getValor());

        }

        repository.deleteById(lancamento.getId());

        return lancamento;

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

        lancamentoDTO.setEfetivado((lancamentoDTO.getDataLancamento().isBefore(LocalDate.now()) ||
                lancamentoDTO.getDataLancamento().isEqual(LocalDate.now())) &&
                lancamentoDTO.getEfetivado().equals(false) ||
                lancamentoDTO.getEfetivado());

        return lancamentoDTO;

    }

    @Override
    public LancamentoRepository getRepository() {
        return repository;
    }

    @Override
    public LancamentoMapper getMapper() {
        return mapper;
    }

    public List<LancamentoDTO> recuperarLancamentosUsuario(Long userId) throws NegocialException {

        return repository.findByDonoId(usuarioService.recuperar(userId).getId()).stream().map(mapper::toDto).toList();

    }

    public void excluirLancamentosConta(Long conta) throws NegocialException {

        for (Long lancamento : repository.findIdLancamentoByContaId(conta)) {

            excluir(lancamento);

        }

    }

    public LancamentoDTO efetivar(EfetivaLancamentoDTO dto) throws NegocialException {

        LancamentoDTO lancamento = recuperar(dto.getLancamentoId());

        if (lancamento.getEfetivado()) {

            throw new LancamentoJaEfetivadoException();

        }

        try {

            lancamento.setContaDono(dto.getDonoId());
            lancamento.setEfetivado(true);

            return salvar(lancamento);

        } catch (ContaComDonoErradoException e) {

            throw new LancamentoNaoPertenceAoUsuarioException();

        }

    }

    private void compararOperar(LancamentoDTO dto, Lancamento entidade) throws NegocialException {

        if (Objects.nonNull(entidade) && !dto.getValor().equals(entidade.getValor())) {

            operar(entidade.getTipo().equals(TipoLancamentoEnum.ENTRADA) ? TipoLancamentoEnum.SAIDA : TipoLancamentoEnum.ENTRADA,
                    dto.getContaId(), entidade.getValor());

            operar(dto.getTipo(), dto.getContaId(), dto.getValor());

        }

    }

    private void operar(TipoLancamentoEnum tipo, Long contaId, Double valor) throws NegocialException {

        if (tipo.equals(TipoLancamentoEnum.ENTRADA)) {

            contaService.atualizarSaldo(valor, contaId);

        } else {

            contaService.atualizarSaldo(-valor, contaId);

        }

    }

    public List<LancamentoDTO> recuperarLancamentosConta(Long conta) throws NegocialException {

        return repository.findByContaId(contaService.recuperar(conta).getId()).stream().map(mapper::toDto).toList();

    }
}
