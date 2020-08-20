package com.miage.app.repository;

import com.miage.app.domain.Admin;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Admin entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
}
