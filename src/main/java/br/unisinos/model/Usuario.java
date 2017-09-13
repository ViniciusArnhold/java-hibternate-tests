package br.unisinos.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "USUARIO")
@Inheritance(strategy = InheritanceType.JOINED)
@XmlRootElement
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "USUARIO_ID")
    private long id;

    private String nome;

    private String telefone;

    private String email;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "INTERESSADOS_ANUNCIO", joinColumns = {
            @JoinColumn(name = "USUARIO_ID")
    }, inverseJoinColumns = {
            @JoinColumn(name = "ANUNCIO_ID")
    })
    private Set<Anuncio> interesses;

    @XmlElement
    public Set<Anuncio> getInteresses() {
        return this.interesses;
    }

    @XmlElement
    public long getId() {
        return this.id;
    }

    @XmlElement
    public String getNome() {
        return this.nome;
    }

    @XmlElement
    public String getTelefone() {
        return this.telefone;
    }

    public Usuario setInteresses(Set<Anuncio> interesses) {
        this.interesses = interesses;
        return this;
    }

    public Usuario addInteresse(Anuncio anuncio) {
        if (this.interesses == null) {
            this.interesses = new HashSet<>();
        }
        this.interesses.add(anuncio);
        return this;
    }


    public Usuario setNome(String nome) {
        this.nome = nome;
        return this;
    }


    public Usuario setTelefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    @XmlElement
    public String getEmail() {
        return this.email;
    }

    public Usuario setEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + this.id +
                ", nome='" + this.nome + '\'' +
                ", telefone='" + this.telefone + '\'' +
                ", email='" + this.email + '\'' +
                ", interesses=" + this.interesses +
                '}';
    }
}
