package com.santi.dicccionario.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.santi.dicccionario.domain.Pais} entity.
 */
public class PaisDTO implements Serializable {

    @NotNull
    @Size(max = 2)
    private String codigoPais;

    @NotNull
    @Size(max = 100)
    private String nombrePais;

    public String getCodigoPais() {
        return codigoPais;
    }

    public void setCodigoPais(String codigoPais) {
        this.codigoPais = codigoPais;
    }

    public String getNombrePais() {
        return nombrePais;
    }

    public void setNombrePais(String nombrePais) {
        this.nombrePais = nombrePais;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaisDTO)) {
            return false;
        }

        PaisDTO paisDTO = (PaisDTO) o;
        if (this.codigoPais == null) {
            return false;
        }
        return Objects.equals(this.codigoPais, paisDTO.codigoPais);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.codigoPais);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaisDTO{" +
            "codigoPais='" + getCodigoPais() + "'" +
            ", nombrePais='" + getNombrePais() + "'" +
            "}";
    }
}
