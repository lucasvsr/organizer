package com.lvsr.organizer.app.repositories;

import com.lvsr.organizer.app.models.Instituicao;
import com.lvsr.organizer.app.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstituicaoRepository extends JpaRepository<Instituicao, Long> {

}
