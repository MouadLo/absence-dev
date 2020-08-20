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
 * A Etudiant.
 */
@Entity
@Table(name = "etudiant")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Etudiant implements Serializable {

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
    @Column(name = "cne", nullable = false)
    private String cne;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotNull
    @Column(name = "prenom", nullable = false, unique = true)
    private String prenom;

    
    @Column(name = "tel", unique = true)
    private String tel;

    @ManyToOne
    @JsonIgnoreProperties(value = "etudiants", allowSetters = true)
    private Classe classe;

    @OneToMany(mappedBy = "etudiant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Absence> absences = new HashSet<>();

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

    public Etudiant login(String login) {
        this.login = login;
        return this;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMdp() {
        return mdp;
    }

    public Etudiant mdp(String mdp) {
        this.mdp = mdp;
        return this;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getCne() {
        return cne;
    }

    public Etudiant cne(String cne) {
        this.cne = cne;
        return this;
    }

    public void setCne(String cne) {
        this.cne = cne;
    }

    public String getEmail() {
        return email;
    }

    public Etudiant email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNom() {
        return nom;
    }

    public Etudiant nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Etudiant prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTel() {
        return tel;
    }

    public Etudiant tel(String tel) {
        this.tel = tel;
        return this;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Classe getClasse() {
        return classe;
    }

    public Etudiant classe(Classe classe) {
        this.classe = classe;
        return this;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    public Set<Absence> getAbsences() {
        return absences;
    }

    public Etudiant absences(Set<Absence> absences) {
        this.absences = absences;
        return this;
    }

    public Etudiant addAbsence(Absence absence) {
        this.absences.add(absence);
        absence.setEtudiant(this);
        return this;
    }

    public Etudiant removeAbsence(Absence absence) {
        this.absences.remove(absence);
        absence.setEtudiant(null);
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
        if (!(o instanceof Etudiant)) {
            return false;
        }
        return id != null && id.equals(((Etudiant) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Etudiant{" +
            "id=" + getId() +
            ", login='" + getLogin() + "'" +
            ", mdp='" + getMdp() + "'" +
            ", cne='" + getCne() + "'" +
            ", email='" + getEmail() + "'" +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", tel='" + getTel() + "'" +
            "}";
    }
}
