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

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false)
    private String email;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "INTERESSADOS_ANUNCIO", joinColumns = {
            @JoinColumn(name = "USUARIO_ID")
    }, inverseJoinColumns = {
            @JoinColumn(name = "ANUNCIO_ID")
    })
    private Set<Anuncio> interesses = new HashSet<>();

    @XmlElement(required = true)
    public Set<Anuncio> getInteresses() {
        return this.interesses;
    }

    public Usuario setInteresses(Set<Anuncio> interesses) {
        this.interesses = interesses;
        return this;
    }

    @XmlElement(required = true)
    public long getId() {
        return this.id;
    }

    @XmlElement(required = true)
    public String getNome() {
        return this.nome;
    }

    public Usuario setNome(String nome) {
        this.nome = nome;
        return this;
    }

    @XmlElement(required = true)
    public String getTelefone() {
        return this.telefone;
    }

    public Usuario setTelefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    public Usuario addInteresse(Anuncio anuncio) {
        if (this.interesses == null) {
            this.interesses = new HashSet<>();
        }
        this.interesses.add(anuncio);
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
