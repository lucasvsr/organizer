package com.lvsr.organizer.app.services;

import com.lvsr.organizer.app.dtos.InstituicaoDTO;
import com.lvsr.organizer.app.exceptions.InstituicaoJaCadastradaException;
import com.lvsr.organizer.app.exceptions.InstituicaoNaoEncontradaException;
import com.lvsr.organizer.app.exceptions.NegocialException;
import com.lvsr.organizer.app.interfaces.IService;
import com.lvsr.organizer.app.mappers.InstituicaoMapper;
import com.lvsr.organizer.app.models.Instituicao;
import com.lvsr.organizer.app.repositories.InstituicaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InstituicaoService implements IService<InstituicaoDTO> {

    @Autowired
    private InstituicaoRepository repository;

    @Autowired
    private InstituicaoMapper mapper;

    @Override
    public InstituicaoDTO salvar(InstituicaoDTO instituicaoDTO) throws NegocialException {

        Instituicao entidade = mapper.toModel(validar(instituicaoDTO));

        return mapper.toDto(repository.save(entidade));

    }

    @Override
    public InstituicaoDTO excluir(Long id) throws NegocialException {

        Optional<Instituicao> query = repository.findById(id);

        if(query.isPresent()) {

            repository.delete(query.get());

            return mapper.toDto(query.get());

        } else {

            throw new InstituicaoNaoEncontradaException();

        }

    }

    @Override
    public InstituicaoDTO recuperar(Long id) throws NegocialException {
        return id == null || id == 0L ? null : mapper.toDto(repository.findById(id).orElseThrow(InstituicaoNaoEncontradaException::new));
    }

    @Override
    public InstituicaoDTO validar(InstituicaoDTO instituicaoDTO) throws NegocialException {

        if(repository.findAll().stream().anyMatch(instituicao -> instituicao.getNome().equals(instituicaoDTO.getNome()))) {

            throw new InstituicaoJaCadastradaException();

        }

        return instituicaoDTO;

    }
}
