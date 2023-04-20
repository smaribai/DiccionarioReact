package com.santi.dicccionario.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.santi.dicccionario.domain.Cliente} entity.
 */
public class ClienteDTO implements Serializable {

    @NotNull
    @Size(max = 20)
    private String idCliente;

    @NotNull
    @Size(max = 70)
    private String nombre;

    @Size(max = 200)
    private String descripcion;

    @Size(max = 200)
    private String direccion;

    private TipoClienteDTO tipoCliente;

    private PaisDTO pais;

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public TipoClienteDTO getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(TipoClienteDTO tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public PaisDTO getPais() {
        return pais;
    }

    public void setPais(PaisDTO pais) {
        this.pais = pais;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClienteDTO)) {
            return false;
        }

        ClienteDTO clienteDTO = (ClienteDTO) o;
        if (this.idCliente == null) {
            return false;
        }
        return Objects.equals(this.idCliente, clienteDTO.idCliente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idCliente);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClienteDTO{" +
            "idCliente='" + getIdCliente() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", tipoCliente=" + getTipoCliente() +
            ", pais=" + getPais() +
            "}";
    }
}
