package com.lvsr.organizer.app.repositories;

import com.lvsr.organizer.app.models.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {
    List<Conta> findByInstituicaoId(Long instituicaoId);
}
