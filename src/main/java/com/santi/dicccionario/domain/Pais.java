package com.santi.dicccionario.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

/**
 * A Pais.
 */
@Entity
@Table(name = "pais")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Pais implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 2)
    @Id
    @Column(name = "codigo_pais", length = 2, nullable = false)
    private String codigoPais;

    @NotNull
    @Size(max = 100)
    @Column(name = "nombre_pais", length = 100, nullable = false)
    private String nombrePais;

    @Transient
    private boolean isPersisted;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getCodigoPais() {
        return this.codigoPais;
    }

    public Pais codigoPais(String codigoPais) {
        this.setCodigoPais(codigoPais);
        return this;
    }

    public void setCodigoPais(String codigoPais) {
        this.codigoPais = codigoPais;
    }

    public String getNombrePais() {
        return this.nombrePais;
    }

    public Pais nombrePais(String nombrePais) {
        this.setNombrePais(nombrePais);
        return this;
    }

    public void setNombrePais(String nombrePais) {
        this.nombrePais = nombrePais;
    }

    @Override
    public String getId() {
        return this.codigoPais;
    }

    @Transient
    @Override
    public boolean isNew() {
        return !this.isPersisted;
    }

    public Pais setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    @PostLoad
    @PostPersist
    public void updateEntityState() {
        this.setIsPersisted();
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pais)) {
            return false;
        }
        return codigoPais != null && codigoPais.equals(((Pais) o).codigoPais);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Pais{" +
            "codigoPais=" + getCodigoPais() +
            ", nombrePais='" + getNombrePais() + "'" +
            "}";
    }
}
