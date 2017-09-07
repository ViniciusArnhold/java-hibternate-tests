package br.unisinos.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "USUARIO")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
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

    public Usuario() {

    }

    public Set<Anuncio> getInteresses() {
        return interesses;
    }

    public Usuario setInteresses(Set<Anuncio> interesses) {
        this.interesses = interesses;
        return this;
    }

    public Usuario addInteresse(Anuncio anuncio) {
        if (interesses == null) {
            this.interesses = new HashSet<>();
        }
        this.interesses.add(anuncio);
        return this;
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Usuario setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public String getTelefone() {
        return telefone;
    }

    public Usuario setTelefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Usuario setEmail(String email) {
        this.email = email;
        return this;
    }
}
