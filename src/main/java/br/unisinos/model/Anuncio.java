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

    @XmlElement(required = true)
    public String getTitulo() {
        return this.titulo;
    }

    @XmlElement(required = true)
    public String getDescricao() {
        return this.descricao;
    }

    @XmlElement(required = true)
    public Date getDataPublicacao() {
        return this.dataPublicacao;
    }

    @XmlElement(required = true)
    public long getId() {
        return this.id;
    }

    @XmlElement(required = true)
    public double getValor() {
        return this.valor;
    }

    @XmlElement(required = false, defaultValue = "false")
    public boolean isDisponivel() {
        return this.disponivel;
    }

    @XmlElement(required = true)
    public Anunciante getAnunciante() {
        return this.anunciante;
    }

    public Anuncio setTitulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public Anuncio setDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public Anuncio setDataPublicacao(Date dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
        return this;
    }

    public Anuncio setValor(double valor) {
        this.valor = valor;
        return this;
    }

    public Anuncio setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
        return this;
    }

    @Override
    public String toString() {
        return "Anuncio{" +
                "id=" + this.id +
                ", titulo='" + this.titulo + '\'' +
                ", descricao='" + this.descricao + '\'' +
                ", dataPublicacao=" + this.dataPublicacao +
                ", valor=" + this.valor +
                ", disponivel=" + this.disponivel +
                ", idAnunciante=" + this.anunciante.getId() +
                '}';
    }
}
