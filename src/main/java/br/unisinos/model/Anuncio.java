package br.unisinos.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

@Entity
@Table(name = "ANUNCIO")
public class Anuncio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ANUNCIO_ID")
    private long id;

    @Column(nullable = false)
    private String titulo;

    @Column(length = 225)
    private String descricao;

    @Column(nullable = false)
    private ZonedDateTime dataPublicacao;

    @Column(nullable = false)
    private double valor;

    @Column(nullable = false)
    private boolean disponivel;

    @ManyToOne
    @JoinColumn(name = "ANUNCIANTE_ID")
    private Anunciante anunciante;

    public Anuncio(Anunciante anunciante) {
        this.anunciante = Objects.requireNonNull(anunciante);
    }

    public long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Anuncio setTitulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public String getDescricao() {
        return descricao;
    }

    public Anuncio setDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public ZonedDateTime getDataPublicacao() {
        return dataPublicacao;
    }

    public Anuncio setDataPublicacao(ZonedDateTime dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
        return this;
    }

    public double getValor() {
        return valor;
    }

    public Anuncio setValor(double valor) {
        this.valor = valor;
        return this;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public Anuncio setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
        return this;
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
