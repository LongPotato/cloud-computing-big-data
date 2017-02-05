package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Citizen.
 */
@Entity
@Table(name = "citizen")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Citizen implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "dob")
    private ZonedDateTime dob;

    @Column(name = "gender")
    private String gender;

    @OneToOne
    @JoinColumn(unique = true)
    private Passport passport;

    @ManyToOne
    private Country country;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Citizen name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getDob() {
        return dob;
    }

    public Citizen dob(ZonedDateTime dob) {
        this.dob = dob;
        return this;
    }

    public void setDob(ZonedDateTime dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public Citizen gender(String gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Passport getPassport() {
        return passport;
    }

    public Citizen passport(Passport passport) {
        this.passport = passport;
        return this;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }

    public Country getCountry() {
        return country;
    }

    public Citizen country(Country country) {
        this.country = country;
        return this;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Citizen citizen = (Citizen) o;
        if (citizen.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, citizen.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Citizen{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", dob='" + dob + "'" +
            ", gender='" + gender + "'" +
            '}';
    }
}
