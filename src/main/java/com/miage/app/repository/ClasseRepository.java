package com.miage.app.repository;

import com.miage.app.domain.Classe;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Classe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClasseRepository extends JpaRepository<Classe, Long> {
}
