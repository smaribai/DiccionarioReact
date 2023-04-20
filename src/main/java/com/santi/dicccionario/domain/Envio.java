package com.santi.dicccionario.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Envio.
 */
@Entity
@Table(name = "envio")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Envio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "cliente")
    private String cliente;

    @Column(name = "remitente")
    private String remitente;

    @Column(name = "destinatario")
    private String destinatario;

    @Column(name = "proveedor")
    private String proveedor;

    @Column(name = "ref_remitente")
    private String refRemitente;

    @Column(name = "ref_destinatario")
    private String refDestinatario;

    @Column(name = "codigo_arancelario_origen")
    private String codigoArancelarioOrigen;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "importe")
    private Double importe;

    @Column(name = "provincia_destino")
    private String provinciaDestino;

    @Column(name = "uds")
    private Integer uds;

    @Column(name = "peso")
    private Double peso;

    @OneToOne
    @JoinColumn(unique = true)
    private Pais paisOrigen;

    @OneToOne
    @JoinColumn(unique = true)
    private Pais paisDestino;

    @OneToOne
    @JoinColumn(unique = true)
    private Divisa divisa;

    @OneToOne
    @JoinColumn(unique = true)
    private Idioma idioma;

    @JsonIgnoreProperties(value = { "tipoCliente", "pais" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Cliente refCliente;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Envio id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCliente() {
        return this.cliente;
    }

    public Envio cliente(String cliente) {
        this.setCliente(cliente);
        return this;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getRemitente() {
        return this.remitente;
    }

    public Envio remitente(String remitente) {
        this.setRemitente(remitente);
        return this;
    }

    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

    public String getDestinatario() {
        return this.destinatario;
    }

    public Envio destinatario(String destinatario) {
        this.setDestinatario(destinatario);
        return this;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getProveedor() {
        return this.proveedor;
    }

    public Envio proveedor(String proveedor) {
        this.setProveedor(proveedor);
        return this;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getRefRemitente() {
        return this.refRemitente;
    }

    public Envio refRemitente(String refRemitente) {
        this.setRefRemitente(refRemitente);
        return this;
    }

    public void setRefRemitente(String refRemitente) {
        this.refRemitente = refRemitente;
    }

    public String getRefDestinatario() {
        return this.refDestinatario;
    }

    public Envio refDestinatario(String refDestinatario) {
        this.setRefDestinatario(refDestinatario);
        return this;
    }

    public void setRefDestinatario(String refDestinatario) {
        this.refDestinatario = refDestinatario;
    }

    public String getCodigoArancelarioOrigen() {
        return this.codigoArancelarioOrigen;
    }

    public Envio codigoArancelarioOrigen(String codigoArancelarioOrigen) {
        this.setCodigoArancelarioOrigen(codigoArancelarioOrigen);
        return this;
    }

    public void setCodigoArancelarioOrigen(String codigoArancelarioOrigen) {
        this.codigoArancelarioOrigen = codigoArancelarioOrigen;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Envio descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getImporte() {
        return this.importe;
    }

    public Envio importe(Double importe) {
        this.setImporte(importe);
        return this;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public String getProvinciaDestino() {
        return this.provinciaDestino;
    }

    public Envio provinciaDestino(String provinciaDestino) {
        this.setProvinciaDestino(provinciaDestino);
        return this;
    }

    public void setProvinciaDestino(String provinciaDestino) {
        this.provinciaDestino = provinciaDestino;
    }

    public Integer getUds() {
        return this.uds;
    }

    public Envio uds(Integer uds) {
        this.setUds(uds);
        return this;
    }

    public void setUds(Integer uds) {
        this.uds = uds;
    }

    public Double getPeso() {
        return this.peso;
    }

    public Envio peso(Double peso) {
        this.setPeso(peso);
        return this;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Pais getPaisOrigen() {
        return this.paisOrigen;
    }

    public void setPaisOrigen(Pais pais) {
        this.paisOrigen = pais;
    }

    public Envio paisOrigen(Pais pais) {
        this.setPaisOrigen(pais);
        return this;
    }

    public Pais getPaisDestino() {
        return this.paisDestino;
    }

    public void setPaisDestino(Pais pais) {
        this.paisDestino = pais;
    }

    public Envio paisDestino(Pais pais) {
        this.setPaisDestino(pais);
        return this;
    }

    public Divisa getDivisa() {
        return this.divisa;
    }

    public void setDivisa(Divisa divisa) {
        this.divisa = divisa;
    }

    public Envio divisa(Divisa divisa) {
        this.setDivisa(divisa);
        return this;
    }

    public Idioma getIdioma() {
        return this.idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public Envio idioma(Idioma idioma) {
        this.setIdioma(idioma);
        return this;
    }

    public Cliente getRefCliente() {
        return this.refCliente;
    }

    public void setRefCliente(Cliente cliente) {
        this.refCliente = cliente;
    }

    public Envio refCliente(Cliente cliente) {
        this.setRefCliente(cliente);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Envio)) {
            return false;
        }
        return id != null && id.equals(((Envio) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Envio{" +
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
            "}";
    }
}
