package br.unisinos.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "ANUNCIO")
@XmlRootElement
public class Anuncio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ANUNCIO_ID")
    private long id;

    @Column(nullable = false)
    private String titulo;

    @Column(length = 225)
    private String descricao;

    //JAXB nao suporta java.time
    @Column(nullable = false)
    private Date dataPublicacao;

    @Column(nullable = false)
    private double valor;

    @Column(nullable = false)
    private boolean disponivel;

    @ManyToOne
    @JoinColumn(name = "ANUNCIANTE_ID")
    private Anunciante anunciante;

    public Anuncio() {

    }

    public Anuncio(Anunciante anunciante) {
        this.anunciante = Objects.requireNonNull(anunciante);
    }

    @XmlElement
    public long getId() {
        return id;
    }

    @XmlElement
    public String getTitulo() {
        return titulo;
    }

    public Anuncio setTitulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    @XmlElement
    public String getDescricao() {
        return descricao;
    }

    public Anuncio setDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    @XmlElement
    public Date getDataPublicacao() {
        return dataPublicacao;
    }

    public Anuncio setDataPublicacao(Date dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
        return this;
    }

    @XmlElement
    public double getValor() {
        return valor;
    }

    public Anuncio setValor(double valor) {
        this.valor = valor;
        return this;
    }

    @XmlElement
    public boolean isDisponivel() {
        return disponivel;
    }

    public Anuncio setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
        return this;
    }

    @XmlElement
    public Anunciante getAnunciante() {
        return anunciante;
    }

    @Override
    public String toString() {
        return "Anuncio{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", dataPublicacao=" + dataPublicacao +
                ", valor=" + valor +
                ", disponivel=" + disponivel +
                ", idAnunciante=" + anunciante.getNomeFantasia() +
                '}';
    }
}
