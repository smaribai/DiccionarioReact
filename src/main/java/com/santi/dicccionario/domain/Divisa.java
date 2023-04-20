package com.santi.dicccionario.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

/**
 * A Divisa.
 */
@Entity
@Table(name = "divisa")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Divisa implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 3)
    @Id
    @Column(name = "codigo_divisa", length = 3, nullable = false)
    private String codigoDivisa;

    @NotNull
    @Size(max = 100)
    @Column(name = "nombre_divisa", length = 100, nullable = false)
    private String nombreDivisa;

    @Transient
    private boolean isPersisted;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getCodigoDivisa() {
        return this.codigoDivisa;
    }

    public Divisa codigoDivisa(String codigoDivisa) {
        this.setCodigoDivisa(codigoDivisa);
        return this;
    }

    public void setCodigoDivisa(String codigoDivisa) {
        this.codigoDivisa = codigoDivisa;
    }

    public String getNombreDivisa() {
        return this.nombreDivisa;
    }

    public Divisa nombreDivisa(String nombreDivisa) {
        this.setNombreDivisa(nombreDivisa);
        return this;
    }

    public void setNombreDivisa(String nombreDivisa) {
        this.nombreDivisa = nombreDivisa;
    }

    @Override
    public String getId() {
        return this.codigoDivisa;
    }

    @Transient
    @Override
    public boolean isNew() {
        return !this.isPersisted;
    }

    public Divisa setIsPersisted() {
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
        if (!(o instanceof Divisa)) {
            return false;
        }
        return codigoDivisa != null && codigoDivisa.equals(((Divisa) o).codigoDivisa);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Divisa{" +
            "codigoDivisa=" + getCodigoDivisa() +
            ", nombreDivisa='" + getNombreDivisa() + "'" +
            "}";
    }
}
