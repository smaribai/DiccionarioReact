package com.santi.dicccionario.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.santi.dicccionario.domain.Divisa} entity.
 */
public class DivisaDTO implements Serializable {

    @NotNull
    @Size(max = 3)
    private String codigoDivisa;

    @NotNull
    @Size(max = 100)
    private String nombreDivisa;

    public String getCodigoDivisa() {
        return codigoDivisa;
    }

    public void setCodigoDivisa(String codigoDivisa) {
        this.codigoDivisa = codigoDivisa;
    }

    public String getNombreDivisa() {
        return nombreDivisa;
    }

    public void setNombreDivisa(String nombreDivisa) {
        this.nombreDivisa = nombreDivisa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DivisaDTO)) {
            return false;
        }

        DivisaDTO divisaDTO = (DivisaDTO) o;
        if (this.codigoDivisa == null) {
            return false;
        }
        return Objects.equals(this.codigoDivisa, divisaDTO.codigoDivisa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.codigoDivisa);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DivisaDTO{" +
            "codigoDivisa='" + getCodigoDivisa() + "'" +
            ", nombreDivisa='" + getNombreDivisa() + "'" +
            "}";
    }
}
