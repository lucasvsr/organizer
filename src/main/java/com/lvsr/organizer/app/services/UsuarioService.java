package com.lvsr.organizer.app.services;

import com.lvsr.organizer.app.dtos.ContaDTO;
import com.lvsr.organizer.app.dtos.UsuarioDTO;
import com.lvsr.organizer.app.exceptions.EmailJaExisteException;
import com.lvsr.organizer.app.exceptions.NegocialException;
import com.lvsr.organizer.app.exceptions.UsuarioNaoEncontradoException;
import com.lvsr.organizer.app.interfaces.IService;
import com.lvsr.organizer.app.mappers.UsuarioMapper;
import com.lvsr.organizer.app.models.Usuario;
import com.lvsr.organizer.app.repositories.UsuarioRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Service
public class UsuarioService implements IService<UsuarioDTO, UsuarioRepository, UsuarioMapper> {

    private final UsuarioRepository repository;
    private final ContaService contaService;

    private final UsuarioMapper mapper;

    public UsuarioService(UsuarioRepository repository, @Lazy ContaService contaService, UsuarioMapper mapper) {
        this.repository = repository;
        this.contaService = contaService;
        this.mapper = mapper;
    }

    @Override
    public UsuarioDTO salvar(UsuarioDTO usuarioDTO) throws NegocialException {

        return mapper.toDto(repository.save(mapper.toModel(validar(usuarioDTO))));

    }

    @Override
    public UsuarioDTO excluir(Long id) throws NegocialException {

        UsuarioDTO dto = recuperar(id);

        for (ContaDTO conta : dto.getContas()) {

            contaService.excluir(conta.getId());

        }

        repository.deleteById(dto.getId());

        return dto;


    }

    @Override
    public UsuarioDTO recuperar(Long id) throws NegocialException {

        if (id == null || id == 0L) {

            throw new UsuarioNaoEncontradoException();

        }

        return mapper.toDto(repository.findById(id).orElseThrow(UsuarioNaoEncontradoException::new));

    }

    @Override
    public UsuarioDTO validar(UsuarioDTO usuarioDTO) throws NegocialException {

        Usuario usuarioEmail = StringUtils.hasText(usuarioDTO.getEmail()) ?
                repository.findByEmail(usuarioDTO.getEmail()).orElse(null) : null;

        if (Objects.nonNull(usuarioEmail) && !usuarioEmail.getId().equals(usuarioDTO.getId())) {

            throw new EmailJaExisteException();

        }

        return usuarioDTO;

    }

    @Override
    public UsuarioRepository getRepository() {
        return repository;
    }

    @Override
    public UsuarioMapper getMapper() {
        return mapper;
    }
}
