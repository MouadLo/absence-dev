package com.miage.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Filiere.
 */
@Entity
@Table(name = "filiere")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Filiere implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "id_filiere", nullable = false)
    private Integer idFiliere;

    @NotNull
    @Column(name = "nom_filiere", nullable = false)
    private String nomFiliere;

    @NotNull
    @Column(name = "abreviation", nullable = false)
    private String abreviation;

    @OneToMany(mappedBy = "filiere")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Classe> classes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdFiliere() {
        return idFiliere;
    }

    public Filiere idFiliere(Integer idFiliere) {
        this.idFiliere = idFiliere;
        return this;
    }

    public void setIdFiliere(Integer idFiliere) {
        this.idFiliere = idFiliere;
    }

    public String getNomFiliere() {
        return nomFiliere;
    }

    public Filiere nomFiliere(String nomFiliere) {
        this.nomFiliere = nomFiliere;
        return this;
    }

    public void setNomFiliere(String nomFiliere) {
        this.nomFiliere = nomFiliere;
    }

    public String getAbreviation() {
        return abreviation;
    }

    public Filiere abreviation(String abreviation) {
        this.abreviation = abreviation;
        return this;
    }

    public void setAbreviation(String abreviation) {
        this.abreviation = abreviation;
    }

    public Set<Classe> getClasses() {
        return classes;
    }

    public Filiere classes(Set<Classe> classes) {
        this.classes = classes;
        return this;
    }

    public Filiere addClasse(Classe classe) {
        this.classes.add(classe);
        classe.setFiliere(this);
        return this;
    }

    public Filiere removeClasse(Classe classe) {
        this.classes.remove(classe);
        classe.setFiliere(null);
        return this;
    }

    public void setClasses(Set<Classe> classes) {
        this.classes = classes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Filiere)) {
            return false;
        }
        return id != null && id.equals(((Filiere) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Filiere{" +
            "id=" + getId() +
            ", idFiliere=" + getIdFiliere() +
            ", nomFiliere='" + getNomFiliere() + "'" +
            ", abreviation='" + getAbreviation() + "'" +
            "}";
    }
}
