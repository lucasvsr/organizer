package com.lvsr.organizer.app.services;

import com.lvsr.organizer.app.dtos.ContaDTO;
import com.lvsr.organizer.app.dtos.InstituicaoDTO;
import com.lvsr.organizer.app.dtos.UsuarioDTO;
import com.lvsr.organizer.app.exceptions.*;
import com.lvsr.organizer.app.interfaces.IService;
import com.lvsr.organizer.app.mappers.ContaMapper;
import com.lvsr.organizer.app.models.Conta;
import com.lvsr.organizer.app.repositories.ContaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ContaService implements IService<ContaDTO> {

    @Autowired
    private ContaRepository repository;
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private InstituicaoService instituicaoService;

    @Autowired
    private LancamentoService lancamentoService;

    @Autowired
    private ContaMapper mapper;

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

        if (id == null || id == 0L) {

            throw new ContaInexistenteException();

        }

        return mapper.toDto(repository.findById(id).orElseThrow(ContaInexistenteException::new));

    }

    @Override
    public ContaDTO validar(ContaDTO contaDTO) throws NegocialException {

        boolean temId = Objects.nonNull(contaDTO.getId());
        Conta conta = temId ? mapper.toModel(recuperar(contaDTO.getId())) : null;
        UsuarioDTO dono;
        InstituicaoDTO instituicao = instituicaoService.recuperar(contaDTO.getInstituicaoId());

        if (contaDTO.getSaldo() < 0) {
            throw new ContaAbaixoDeZeroException();
        }

        try {

            dono = usuarioService.recuperar(contaDTO.getDonoId());

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

    public void atualizarSaldo(Double valor, ContaDTO conta) throws NegocialException {

        conta.setSaldo(conta.getSaldo() + valor);

        salvar(conta);

    }

    public void excluirContasInstituicao(Long instituicaoId) throws NegocialException {

        for (Conta conta: repository.findByInstituicaoId(instituicaoId)) {

            excluir(conta.getId());

        }

    }
}
