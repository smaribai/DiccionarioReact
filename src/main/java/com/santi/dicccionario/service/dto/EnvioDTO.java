package com.santi.dicccionario.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.santi.dicccionario.domain.Envio} entity.
 */
public class EnvioDTO implements Serializable {

    private Long id;

    private String cliente;

    private String remitente;

    private String destinatario;

    private String proveedor;

    private String refRemitente;

    private String refDestinatario;

    private String codigoArancelarioOrigen;

    private String descripcion;

    private Double importe;

    private String provinciaDestino;

    private Integer uds;

    private Double peso;

    private PaisDTO paisOrigen;

    private PaisDTO paisDestino;

    private DivisaDTO divisa;

    private IdiomaDTO idioma;

    private ClienteDTO refCliente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getRemitente() {
        return remitente;
    }

    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getRefRemitente() {
        return refRemitente;
    }

    public void setRefRemitente(String refRemitente) {
        this.refRemitente = refRemitente;
    }

    public String getRefDestinatario() {
        return refDestinatario;
    }

    public void setRefDestinatario(String refDestinatario) {
        this.refDestinatario = refDestinatario;
    }

    public String getCodigoArancelarioOrigen() {
        return codigoArancelarioOrigen;
    }

    public void setCodigoArancelarioOrigen(String codigoArancelarioOrigen) {
        this.codigoArancelarioOrigen = codigoArancelarioOrigen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public String getProvinciaDestino() {
        return provinciaDestino;
    }

    public void setProvinciaDestino(String provinciaDestino) {
        this.provinciaDestino = provinciaDestino;
    }

    public Integer getUds() {
        return uds;
    }

    public void setUds(Integer uds) {
        this.uds = uds;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public PaisDTO getPaisOrigen() {
        return paisOrigen;
    }

    public void setPaisOrigen(PaisDTO paisOrigen) {
        this.paisOrigen = paisOrigen;
    }

    public PaisDTO getPaisDestino() {
        return paisDestino;
    }

    public void setPaisDestino(PaisDTO paisDestino) {
        this.paisDestino = paisDestino;
    }

    public DivisaDTO getDivisa() {
        return divisa;
    }

    public void setDivisa(DivisaDTO divisa) {
        this.divisa = divisa;
    }

    public IdiomaDTO getIdioma() {
        return idioma;
    }

    public void setIdioma(IdiomaDTO idioma) {
        this.idioma = idioma;
    }

    public ClienteDTO getRefCliente() {
        return refCliente;
    }

    public void setRefCliente(ClienteDTO refCliente) {
        this.refCliente = refCliente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EnvioDTO)) {
            return false;
        }

        EnvioDTO envioDTO = (EnvioDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, envioDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EnvioDTO{" +
            "id=" + getId() +
            ", cliente='" + getCliente() + "'" +
            ", remitente='" + getRemitente() + "'" +
            ", destinatario='" + getDestinatario() + "'" +
            ", proveedor='" + getProveedor() + "'" +
            ", refRemitente='" + getRefRemitente() + "'" +
            ", refDestinatario='" + getRefDestinatario() + "'" +
            ", codigoArancelarioOrigen='" + getCodigoArancelarioOrigen() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", importe=" + getImporte() +
            ", provinciaDestino='" + getProvinciaDestino() + "'" +
            ", uds=" + getUds() +
            ", peso=" + getPeso() +
            ", paisOrigen=" + getPaisOrigen() +
            ", paisDestino=" + getPaisDestino() +
            ", divisa=" + getDivisa() +
            ", idioma=" + getIdioma() +
            ", refCliente=" + getRefCliente() +
            "}";
    }
}
