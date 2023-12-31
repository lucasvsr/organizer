package com.lvsr.organizer.app.services;

import com.lvsr.organizer.app.dtos.ContaDTO;
import com.lvsr.organizer.app.dtos.UsuarioDTO;
import com.lvsr.organizer.app.exceptions.*;
import com.lvsr.organizer.app.interfaces.IService;
import com.lvsr.organizer.app.mappers.ContaMapper;
import com.lvsr.organizer.app.models.Conta;
import com.lvsr.organizer.app.repositories.ContaRepository;
import com.lvsr.organizer.app.utils.Util;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class ContaService implements IService<ContaDTO, ContaRepository, ContaMapper> {

    private final ContaRepository repository;
    private final UsuarioService usuarioService;
    private final InstituicaoService instituicaoService;
    private final LancamentoService lancamentoService;
    private final ContaMapper mapper;

    public ContaService(ContaRepository repository,
                        UsuarioService usuarioService,
                        @Lazy InstituicaoService instituicaoService,
                        @Lazy LancamentoService lancamentoService, ContaMapper mapper) {
        this.repository = repository;
        this.usuarioService = usuarioService;
        this.instituicaoService = instituicaoService;
        this.lancamentoService = lancamentoService;
        this.mapper = mapper;
    }

    @Override
    public ContaDTO salvar(ContaDTO contaDTO) throws NegocialException {

        return mapper.toDto(repository.save(mapper.toModel(validar(contaDTO))));

    }

    @Override
    public ContaDTO excluir(Long id) throws NegocialException {

        ContaDTO dto = recuperar(id);

        lancamentoService.excluirLancamentosConta(dto.getId());

        repository.deleteById(dto.getId());

        return dto;

    }

    @Override
    public ContaDTO recuperar(Long id) throws NegocialException {

        try {

            return mapper.toDto(repository.findById(Util.notZeroOrNull(id)).get());

        } catch (NullPointerException | NoSuchElementException e) {

            throw new ContaInexistenteException();

        }

    }

    @Override
    public ContaDTO validar(ContaDTO contaDTO) throws NegocialException {

        boolean temId = Objects.nonNull(contaDTO.getId());
        Conta conta = temId ? mapper.toModel(recuperar(contaDTO.getId())) : null;
        UsuarioDTO dono;

        try {

            dono = usuarioService.recuperar(contaDTO.getDonoId());

            instituicaoService.recuperar(contaDTO.getInstituicaoId());

            if (Objects.nonNull(conta) && !conta.getDono().getId().equals(dono.getId())) {

                throw new ContaComDonoErradoException();

            }

            if (!temId && Objects.nonNull(dono.getContas()) && dono.getContas().stream().anyMatch(c -> c.getTipo().equals(contaDTO.getTipo()) &&
                    c.getInstituicaoId().equals(contaDTO.getInstituicaoId()))) {

                throw new ContaDoMesmoTipoJaCriadaNaInstituicaoException();

            }

        } catch (UsuarioNaoEncontradoException e) {

            throw new ContaComDonoErradoException();

        }

        return contaDTO;

    }

    @Override
    public ContaRepository getRepository() {
        return repository;
    }

    @Override
    public ContaMapper getMapper() {
        return mapper;
    }


    public void atualizarSaldo(Double valor, Long id) throws NegocialException {

        ContaDTO conta = recuperar(id);

        conta.setSaldo(conta.getSaldo() + valor);

        salvar(conta);

    }

    public void excluirContasInstituicao(Long instituicaoId) throws NegocialException {

        for (Conta conta : repository.findByInstituicaoId(instituicaoId)) {

            excluir(conta.getId());

        }

    }
}
