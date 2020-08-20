package com.miage.app.repository;

import com.miage.app.domain.Horaire;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Horaire entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HoraireRepository extends JpaRepository<Horaire, Long> {
}
