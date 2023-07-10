package com.lvsr.organizer.app.services;

import com.lvsr.organizer.app.dtos.InstituicaoDTO;
import com.lvsr.organizer.app.exceptions.InstituicaoJaCadastradaException;
import com.lvsr.organizer.app.exceptions.InstituicaoNaoEncontradaException;
import com.lvsr.organizer.app.exceptions.NegocialException;
import com.lvsr.organizer.app.interfaces.IService;
import com.lvsr.organizer.app.mappers.InstituicaoMapper;
import com.lvsr.organizer.app.repositories.InstituicaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstituicaoService implements IService<InstituicaoDTO> {

    @Autowired
    private InstituicaoRepository repository;

    @Autowired
    private InstituicaoMapper mapper;

    @Autowired
    private ContaService contaService;

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

        if (id == null || id == 0L) {

            throw new InstituicaoNaoEncontradaException();

        }

        return mapper.toDto(repository.findById(id).orElseThrow(InstituicaoNaoEncontradaException::new));

    }

    @Override
    public InstituicaoDTO validar(InstituicaoDTO instituicaoDTO) throws NegocialException {

        if (repository.findAll().stream().anyMatch(instituicao -> instituicao.getNome().equals(instituicaoDTO.getNome()))) {

            throw new InstituicaoJaCadastradaException();

        }

        return instituicaoDTO;

    }
}
