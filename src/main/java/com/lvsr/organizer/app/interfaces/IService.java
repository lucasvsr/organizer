package com.lvsr.organizer.app.interfaces;

import com.lvsr.organizer.app.exceptions.NegocialException;

public interface IService<DTO, Repository, Mapper> {

    DTO salvar(DTO dto) throws NegocialException;
    DTO excluir(Long id) throws NegocialException;
    DTO recuperar(Long id) throws NegocialException;
    DTO validar(DTO dto) throws NegocialException;
    Repository getRepository();
    Mapper getMapper();

}
