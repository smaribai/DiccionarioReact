package com.santi.dicccionario.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.santi.dicccionario.domain.TipoCliente} entity.
 */
public class TipoClienteDTO implements Serializable {

    @NotNull
    @Size(max = 3)
    private String codigo;

    @NotNull
    @Size(max = 100)
    private String nombre;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TipoClienteDTO)) {
            return false;
        }

        TipoClienteDTO tipoClienteDTO = (TipoClienteDTO) o;
        if (this.codigo == null) {
            return false;
        }
        return Objects.equals(this.codigo, tipoClienteDTO.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.codigo);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TipoClienteDTO{" +
            "codigo='" + getCodigo() + "'" +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
