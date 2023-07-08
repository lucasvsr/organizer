package com.lvsr.organizer.app.services;

import com.lvsr.organizer.app.dtos.ContaDTO;
import com.lvsr.organizer.app.dtos.UsuarioDTO;
import com.lvsr.organizer.app.exceptions.EmailJaExisteException;
import com.lvsr.organizer.app.exceptions.NegocialException;
import com.lvsr.organizer.app.exceptions.UsuarioNaoEncontradoException;
import com.lvsr.organizer.app.interfaces.IService;
import com.lvsr.organizer.app.mappers.UsuarioMapper;
import com.lvsr.organizer.app.models.Usuario;
import com.lvsr.organizer.app.repositories.ContaRepository;
import com.lvsr.organizer.app.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Service
public class UsuarioService implements IService<UsuarioDTO> {

    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private ContaService contaService;

    @Autowired
    private UsuarioMapper mapper;

    @Override
    public UsuarioDTO salvar(UsuarioDTO usuarioDTO) throws NegocialException {

        Usuario entidade = mapper.toModel(validar(usuarioDTO));

        return mapper.toDto(repository.save(entidade));

    }

    @Override
    public UsuarioDTO excluir(Long id) throws NegocialException {

        UsuarioDTO dto = recuperar(id);

        if (Objects.nonNull(dto)) {

            for (ContaDTO conta : dto.getContas()) {

                contaService.excluir(conta.getId());

            }

            repository.deleteById(dto.getId());

            return dto;

        } else {

            throw new UsuarioNaoEncontradoException();

        }

    }

    @Override
    public UsuarioDTO recuperar(Long id) throws NegocialException {

        return id == null || id == 0L ? null : mapper.toDto(repository.findById(id).orElseThrow(UsuarioNaoEncontradoException::new));

    }

    @Override
    public UsuarioDTO validar(UsuarioDTO usuarioDTO) throws NegocialException {

        boolean temId = Objects.nonNull(usuarioDTO.getId());
        Usuario usuario = temId ? repository.findById(usuarioDTO.getId()).orElse(null) : null;
        Usuario usuarioEmail = StringUtils.hasText(usuarioDTO.getEmail()) ? repository.findByEmail(usuarioDTO.getEmail()).orElse(null) : null;

        if (temId) {

            if (Objects.isNull(usuario)) {

                throw new UsuarioNaoEncontradoException();

            }

        }

        if (Objects.nonNull(usuarioEmail) && !usuarioEmail.getId().equals(usuarioDTO.getId())) {

            throw new EmailJaExisteException();

        }

        return usuarioDTO;

    }
}
