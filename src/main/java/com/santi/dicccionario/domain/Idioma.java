package com.santi.dicccionario.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

/**
 * A Idioma.
 */
@Entity
@Table(name = "idioma")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Idioma implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 2)
    @Id
    @Column(name = "codigo", length = 2, nullable = false)
    private String codigo;

    @NotNull
    @Size(max = 100)
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Transient
    private boolean isPersisted;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getCodigo() {
        return this.codigo;
    }

    public Idioma codigo(String codigo) {
        this.setCodigo(codigo);
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Idioma nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String getId() {
        return this.codigo;
    }

    @Transient
    @Override
    public boolean isNew() {
        return !this.isPersisted;
    }

    public Idioma setIsPersisted() {
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
        if (!(o instanceof Idioma)) {
            return false;
        }
        return codigo != null && codigo.equals(((Idioma) o).codigo);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Idioma{" +
            "codigo=" + getCodigo() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}