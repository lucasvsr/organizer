package com.lvsr.organizer.app.services;

import com.lvsr.organizer.app.dtos.InstituicaoDTO;
import com.lvsr.organizer.app.exceptions.InstituicaoJaCadastradaException;
import com.lvsr.organizer.app.exceptions.InstituicaoNaoEncontradaException;
import com.lvsr.organizer.app.exceptions.NegocialException;
import com.lvsr.organizer.app.interfaces.IService;
import com.lvsr.organizer.app.mappers.InstituicaoMapper;
import com.lvsr.organizer.app.repositories.InstituicaoRepository;
import com.lvsr.organizer.app.utils.Util;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class InstituicaoService implements IService<InstituicaoDTO, InstituicaoRepository, InstituicaoMapper> {

    private final InstituicaoRepository repository;

    private final InstituicaoMapper mapper;

    private final ContaService contaService;

    public InstituicaoService(InstituicaoRepository repository, InstituicaoMapper mapper, ContaService contaService) {
        this.repository = repository;
        this.mapper = mapper;
        this.contaService = contaService;
    }

    @Override
    public InstituicaoDTO salvar(InstituicaoDTO instituicaoDTO) throws NegocialException {

        return mapper.toDto(repository.save(mapper.toModel(validar(instituicaoDTO))));

    }

    @Override
    public InstituicaoDTO excluir(Long id) throws NegocialException {

        InstituicaoDTO instituicao = recuperar(id);

        contaService.excluirContasInstituicao(instituicao.getId());

        repository.deleteById(instituicao.getId());

        return instituicao;


    }

    @Override
    public InstituicaoDTO recuperar(Long id) throws NegocialException {

        try {

            return mapper.toDto(repository.findById(Util.notZeroOrNull(id)).get());

        } catch (NullPointerException | NoSuchElementException e) {

            throw new InstituicaoNaoEncontradaException();

        }

    }

    @Override
    public InstituicaoDTO validar(InstituicaoDTO instituicaoDTO) throws NegocialException {

        if (repository.findAll().stream().anyMatch(instituicao -> instituicao.getNome().equals(instituicaoDTO.getNome()))) {

            throw new InstituicaoJaCadastradaException();

        }

        return instituicaoDTO;

    }

    @Override
    public InstituicaoRepository getRepository() {
        return repository;
    }

    @Override
    public InstituicaoMapper getMapper() {
        return mapper;
    }

}
