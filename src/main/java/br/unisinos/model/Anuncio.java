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
        return this.id;
    }

    @XmlElement
    public String getTitulo() {
        return this.titulo;
    }

    public Anuncio setTitulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    @XmlElement
    public String getDescricao() {
        return this.descricao;
    }

    public Anuncio setDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    @XmlElement
    public Date getDataPublicacao() {
        return this.dataPublicacao;
    }

    public Anuncio setDataPublicacao(Date dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
        return this;
    }

    @XmlElement
    public double getValor() {
        return this.valor;
    }

    public Anuncio setValor(double valor) {
        this.valor = valor;
        return this;
    }

    @XmlElement
    public boolean isDisponivel() {
        return this.disponivel;
    }

    public Anuncio setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
        return this;
    }

    @XmlElement
    public Anunciante getAnunciante() {
        return this.anunciante;
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
