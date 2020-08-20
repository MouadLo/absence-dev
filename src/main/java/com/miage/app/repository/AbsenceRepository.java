package com.miage.app.repository;

import com.miage.app.domain.Absence;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Absence entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AbsenceRepository extends JpaRepository<Absence, Long> {
}
