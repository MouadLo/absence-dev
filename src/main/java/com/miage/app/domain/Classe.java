package com.miage.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Classe.
 */
@Entity
@Table(name = "classe")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Classe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "id_classe", nullable = false)
    private Integer idClasse;

    @NotNull
    @Column(name = "nom_classe", nullable = false)
    private String nomClasse;

    @NotNull
    @Column(name = "niveau", nullable = false)
    private String niveau;

    @ManyToOne
    @JsonIgnoreProperties(value = "classes", allowSetters = true)
    private Filiere filiere;

    @OneToMany(mappedBy = "classe")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Etudiant> etudiants = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdClasse() {
        return idClasse;
    }

    public Classe idClasse(Integer idClasse) {
        this.idClasse = idClasse;
        return this;
    }

    public void setIdClasse(Integer idClasse) {
        this.idClasse = idClasse;
    }

    public String getNomClasse() {
        return nomClasse;
    }

    public Classe nomClasse(String nomClasse) {
        this.nomClasse = nomClasse;
        return this;
    }

    public void setNomClasse(String nomClasse) {
        this.nomClasse = nomClasse;
    }

    public String getNiveau() {
        return niveau;
    }

    public Classe niveau(String niveau) {
        this.niveau = niveau;
        return this;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public Filiere getFiliere() {
        return filiere;
    }

    public Classe filiere(Filiere filiere) {
        this.filiere = filiere;
        return this;
    }

    public void setFiliere(Filiere filiere) {
        this.filiere = filiere;
    }

    public Set<Etudiant> getEtudiants() {
        return etudiants;
    }

    public Classe etudiants(Set<Etudiant> etudiants) {
        this.etudiants = etudiants;
        return this;
    }

    public Classe addEtudiant(Etudiant etudiant) {
        this.etudiants.add(etudiant);
        etudiant.setClasse(this);
        return this;
    }

    public Classe removeEtudiant(Etudiant etudiant) {
        this.etudiants.remove(etudiant);
        etudiant.setClasse(null);
        return this;
    }

    public void setEtudiants(Set<Etudiant> etudiants) {
        this.etudiants = etudiants;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Classe)) {
            return false;
        }
        return id != null && id.equals(((Classe) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Classe{" +
            "id=" + getId() +
            ", idClasse=" + getIdClasse() +
            ", nomClasse='" + getNomClasse() + "'" +
            ", niveau='" + getNiveau() + "'" +
            "}";
    }
}
