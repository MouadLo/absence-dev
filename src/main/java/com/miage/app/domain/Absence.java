package com.miage.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Absence.
 */
@Entity
@Table(name = "absence")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Absence implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "id_absence", nullable = false)
    private Integer idAbsence;

    @ManyToOne
    @JsonIgnoreProperties(value = "absences", allowSetters = true)
    private Horaire horaire;

    @ManyToOne
    @JsonIgnoreProperties(value = "absences", allowSetters = true)
    private Etudiant etudiant;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdAbsence() {
        return idAbsence;
    }

    public Absence idAbsence(Integer idAbsence) {
        this.idAbsence = idAbsence;
        return this;
    }

    public void setIdAbsence(Integer idAbsence) {
        this.idAbsence = idAbsence;
    }

    public Horaire getHoraire() {
        return horaire;
    }

    public Absence horaire(Horaire horaire) {
        this.horaire = horaire;
        return this;
    }

    public void setHoraire(Horaire horaire) {
        this.horaire = horaire;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public Absence etudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
        return this;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Absence)) {
            return false;
        }
        return id != null && id.equals(((Absence) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Absence{" +
            "id=" + getId() +
            ", idAbsence=" + getIdAbsence() +
            "}";
    }
}
