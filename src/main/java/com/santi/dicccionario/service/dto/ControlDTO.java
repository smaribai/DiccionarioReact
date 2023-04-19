package com.santi.dicccionario.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.santi.dicccionario.domain.Control} entity.
 */
public class ControlDTO implements Serializable {

    private Long id;

    private String idControl;

    private String nombre;

    private String descripcion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdControl() {
        return idControl;
    }

    public void setIdControl(String idControl) {
        this.idControl = idControl;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ControlDTO)) {
            return false;
        }

        ControlDTO controlDTO = (ControlDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, controlDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ControlDTO{" +
            "id=" + getId() +
            ", idControl='" + getIdControl() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
