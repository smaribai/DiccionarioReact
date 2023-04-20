package com.santi.dicccionario.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

/**
 * A Cliente.
 */
@Entity
@Table(name = "cliente")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Cliente implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 20)
    @Id
    @Column(name = "id_cliente", length = 20, nullable = false)
    private String idCliente;

    @NotNull
    @Size(max = 70)
    @Column(name = "nombre", length = 70, nullable = false)
    private String nombre;

    @Size(max = 200)
    @Column(name = "descripcion", length = 200)
    private String descripcion;

    @Size(max = 200)
    @Column(name = "direccion", length = 200)
    private String direccion;

    @Transient
    private boolean isPersisted;

    @OneToOne
    @JoinColumn(unique = true)
    private TipoCliente tipoCliente;

    @OneToOne
    @JoinColumn(unique = true)
    private Pais pais;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getIdCliente() {
        return this.idCliente;
    }

    public Cliente idCliente(String idCliente) {
        this.setIdCliente(idCliente);
        return this;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Cliente nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Cliente descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public Cliente direccion(String direccion) {
        this.setDireccion(direccion);
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String getId() {
        return this.idCliente;
    }

    @Transient
    @Override
    public boolean isNew() {
        return !this.isPersisted;
    }

    public Cliente setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    @PostLoad
    @PostPersist
    public void updateEntityState() {
        this.setIsPersisted();
    }

    public TipoCliente getTipoCliente() {
        return this.tipoCliente;
    }

    public void setTipoCliente(TipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public Cliente tipoCliente(TipoCliente tipoCliente) {
        this.setTipoCliente(tipoCliente);
        return this;
    }

    public Pais getPais() {
        return this.pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public Cliente pais(Pais pais) {
        this.setPais(pais);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cliente)) {
            return false;
        }
        return idCliente != null && idCliente.equals(((Cliente) o).idCliente);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cliente{" +
            "idCliente=" + getIdCliente() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", direccion='" + getDireccion() + "'" +
            "}";
    }
}
