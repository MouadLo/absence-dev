package com.miage.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Matiere.
 */
@Entity
@Table(name = "matiere")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Matiere implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "id_matiere", nullable = false)
    private Integer idMatiere;

    @NotNull
    @Column(name = "nom_matiere", nullable = false)
    private String nomMatiere;

    @Column(name = "abreviation")
    private String abreviation;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "matiere_horaire",
               joinColumns = @JoinColumn(name = "matiere_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "horaire_id", referencedColumnName = "id"))
    private Set<Horaire> horaires = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "matiere_prof",
               joinColumns = @JoinColumn(name = "matiere_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "prof_id", referencedColumnName = "id"))
    private Set<Prof> profs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdMatiere() {
        return idMatiere;
    }

    public Matiere idMatiere(Integer idMatiere) {
        this.idMatiere = idMatiere;
        return this;
    }

    public void setIdMatiere(Integer idMatiere) {
        this.idMatiere = idMatiere;
    }

    public String getNomMatiere() {
        return nomMatiere;
    }

    public Matiere nomMatiere(String nomMatiere) {
        this.nomMatiere = nomMatiere;
        return this;
    }

    public void setNomMatiere(String nomMatiere) {
        this.nomMatiere = nomMatiere;
    }

    public String getAbreviation() {
        return abreviation;
    }

    public Matiere abreviation(String abreviation) {
        this.abreviation = abreviation;
        return this;
    }

    public void setAbreviation(String abreviation) {
        this.abreviation = abreviation;
    }

    public Set<Horaire> getHoraires() {
        return horaires;
    }

    public Matiere horaires(Set<Horaire> horaires) {
        this.horaires = horaires;
        return this;
    }

    public Matiere addHoraire(Horaire horaire) {
        this.horaires.add(horaire);
        horaire.getMatieres().add(this);
        return this;
    }

    public Matiere removeHoraire(Horaire horaire) {
        this.horaires.remove(horaire);
        horaire.getMatieres().remove(this);
        return this;
    }

    public void setHoraires(Set<Horaire> horaires) {
        this.horaires = horaires;
    }

    public Set<Prof> getProfs() {
        return profs;
    }

    public Matiere profs(Set<Prof> profs) {
        this.profs = profs;
        return this;
    }

    public Matiere addProf(Prof prof) {
        this.profs.add(prof);
        prof.getMatieres().add(this);
        return this;
    }

    public Matiere removeProf(Prof prof) {
        this.profs.remove(prof);
        prof.getMatieres().remove(this);
        return this;
    }

    public void setProfs(Set<Prof> profs) {
        this.profs = profs;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Matiere)) {
            return false;
        }
        return id != null && id.equals(((Matiere) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Matiere{" +
            "id=" + getId() +
            ", idMatiere=" + getIdMatiere() +
            ", nomMatiere='" + getNomMatiere() + "'" +
            ", abreviation='" + getAbreviation() + "'" +
            "}";
    }
}
