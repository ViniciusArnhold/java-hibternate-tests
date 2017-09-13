package br.unisinos.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ANUNCIANTE")
@XmlRootElement
public class Anunciante extends Usuario implements Serializable {

    @Column(nullable = false)
    private double classificacao;

    @Column(length = 50, nullable = false)
    private String nomeFantasia;

    @JsonIgnore
    @OneToMany(mappedBy = "anunciante", cascade = CascadeType.ALL)
    private transient Set<Anuncio> anuncios;

    public Anunciante() {
    }

    @XmlElement
    public double getClassificacao() {
        return this.classificacao;
    }

    public Anunciante setClassificacao(double classificacao) {
        this.classificacao = classificacao;
        return this;
    }

    @XmlElement
    public String getNomeFantasia() {
        return this.nomeFantasia;
    }

    public Anunciante setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
        return this;
    }

    @Override
    public Anunciante setNome(String nome) {
        super.setNome(nome);
        return this;
    }

    @Override
    public Anunciante setEmail(String email) {
        super.setEmail(email);
        return this;
    }

    @Override
    public Anunciante setTelefone(String telefone) {
        super.setTelefone(telefone);
        return this;
    }

    public Set<Anuncio> getAnuncios() {
        return this.anuncios;
    }

    public Anunciante addAnuncio(Anuncio anuncio) {
        if (this.anuncios == null) {
            this.anuncios = new HashSet<>();
        }
        this.anuncios.add(anuncio);
        return this;
    }

    @Override
    public String toString() {
        return "Anunciante{" +
                "classificacao=" + this.classificacao +
                ", nomeFantasia='" + this.nomeFantasia + '\'' +
                ", anuncios=" + this.anuncios +
                '}';
    }
}
