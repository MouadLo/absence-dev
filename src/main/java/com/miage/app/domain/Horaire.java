package com.miage.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Horaire.
 */
@Entity
@Table(name = "horaire")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Horaire implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "id_horaire", nullable = false)
    private Integer idHoraire;

    @NotNull
    @Column(name = "heure_depart", nullable = false)
    private String heureDepart;

    @NotNull
    @Column(name = "heure_f", nullable = false)
    private String heureF;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ManyToMany(mappedBy = "horaires")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Matiere> matieres = new HashSet<>();

    @OneToMany(mappedBy = "horaire")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Absence> absences = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdHoraire() {
        return idHoraire;
    }

    public Horaire idHoraire(Integer idHoraire) {
        this.idHoraire = idHoraire;
        return this;
    }

    public void setIdHoraire(Integer idHoraire) {
        this.idHoraire = idHoraire;
    }

    public String getHeureDepart() {
        return heureDepart;
    }

    public Horaire heureDepart(String heureDepart) {
        this.heureDepart = heureDepart;
        return this;
    }

    public void setHeureDepart(String heureDepart) {
        this.heureDepart = heureDepart;
    }

    public String getHeureF() {
        return heureF;
    }

    public Horaire heureF(String heureF) {
        this.heureF = heureF;
        return this;
    }

    public void setHeureF(String heureF) {
        this.heureF = heureF;
    }

    public LocalDate getDate() {
        return date;
    }

    public Horaire date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<Matiere> getMatieres() {
        return matieres;
    }

    public Horaire matieres(Set<Matiere> matieres) {
        this.matieres = matieres;
        return this;
    }

    public Horaire addMatiere(Matiere matiere) {
        this.matieres.add(matiere);
        matiere.getHoraires().add(this);
        return this;
    }

    public Horaire removeMatiere(Matiere matiere) {
        this.matieres.remove(matiere);
        matiere.getHoraires().remove(this);
        return this;
    }

    public void setMatieres(Set<Matiere> matieres) {
        this.matieres = matieres;
    }

    public Set<Absence> getAbsences() {
        return absences;
    }

    public Horaire absences(Set<Absence> absences) {
        this.absences = absences;
        return this;
    }

    public Horaire addAbsence(Absence absence) {
        this.absences.add(absence);
        absence.setHoraire(this);
        return this;
    }

    public Horaire removeAbsence(Absence absence) {
        this.absences.remove(absence);
        absence.setHoraire(null);
        return this;
    }

    public void setAbsences(Set<Absence> absences) {
        this.absences = absences;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Horaire)) {
            return false;
        }
        return id != null && id.equals(((Horaire) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Horaire{" +
            "id=" + getId() +
            ", idHoraire=" + getIdHoraire() +
            ", heureDepart='" + getHeureDepart() + "'" +
            ", heureF='" + getHeureF() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
