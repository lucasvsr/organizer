package com.lvsr.organizer.app.interfaces;

import com.lvsr.organizer.app.exceptions.NegocialException;
import com.lvsr.organizer.app.exceptions.UsuarioNaoEncontradoException;
import jakarta.validation.Valid;

public interface IService<DTO> {

    DTO salvar(DTO dto) throws NegocialException;
    DTO excluir(Long id) throws NegocialException;

    DTO recuperar(Long id) throws NegocialException;

    DTO validar(DTO dto) throws NegocialException;

}
