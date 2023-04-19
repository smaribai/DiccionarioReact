package com.santi.dicccionario.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.santi.dicccionario.domain.Producto} entity.
 */
public class ProductoDTO implements Serializable {

    private Long id;

    private String codigoArancelario;

    private String descripcion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoArancelario() {
        return codigoArancelario;
    }

    public void setCodigoArancelario(String codigoArancelario) {
        this.codigoArancelario = codigoArancelario;
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
        if (!(o instanceof ProductoDTO)) {
            return false;
        }

        ProductoDTO productoDTO = (ProductoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductoDTO{" +
            "id=" + getId() +
            ", codigoArancelario='" + getCodigoArancelario() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
