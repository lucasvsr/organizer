package com.lvsr.organizer.app.repositories;

import com.lvsr.organizer.app.models.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

    @Query("FROM lancamentos o WHERE o.conta.dono.id = :user")
    List<Lancamento> findByDonoId(@Param("user") Long user);

    @Query("SELECT o.id FROM lancamentos o WHERE o.conta.id = :id")
    List<Long> findIdLancamentoByContaId(@Param("id") Long id);

    List<Lancamento> findByContaId(Long id);
}
