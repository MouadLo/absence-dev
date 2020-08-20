package com.miage.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Prof.
 */
@Entity
@Table(name = "prof")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Prof implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "login", nullable = false)
    private String login;

    @NotNull
    @Column(name = "mdp", nullable = false)
    private String mdp;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotNull
    @Column(name = "prenom", nullable = false)
    private String prenom;

    @NotNull
    @Column(name = "type", nullable = false)
    private String type;

    @NotNull
    @Column(name = "tel", nullable = false)
    private String tel;

    @ManyToMany(mappedBy = "profs")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Matiere> matieres = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public Prof login(String login) {
        this.login = login;
        return this;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMdp() {
        return mdp;
    }

    public Prof mdp(String mdp) {
        this.mdp = mdp;
        return this;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getEmail() {
        return email;
    }

    public Prof email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNom() {
        return nom;
    }

    public Prof nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Prof prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getType() {
        return type;
    }

    public Prof type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTel() {
        return tel;
    }

    public Prof tel(String tel) {
        this.tel = tel;
        return this;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Set<Matiere> getMatieres() {
        return matieres;
    }

    public Prof matieres(Set<Matiere> matieres) {
        this.matieres = matieres;
        return this;
    }

    public Prof addMatiere(Matiere matiere) {
        this.matieres.add(matiere);
        matiere.getProfs().add(this);
        return this;
    }

    public Prof removeMatiere(Matiere matiere) {
        this.matieres.remove(matiere);
        matiere.getProfs().remove(this);
        return this;
    }

    public void setMatieres(Set<Matiere> matieres) {
        this.matieres = matieres;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Prof)) {
            return false;
        }
        return id != null && id.equals(((Prof) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Prof{" +
            "id=" + getId() +
            ", login='" + getLogin() + "'" +
            ", mdp='" + getMdp() + "'" +
            ", email='" + getEmail() + "'" +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", type='" + getType() + "'" +
            ", tel='" + getTel() + "'" +
            "}";
    }
}
