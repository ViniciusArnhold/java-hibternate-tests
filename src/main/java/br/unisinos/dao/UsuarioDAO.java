package br.unisinos.dao;

import br.unisinos.model.Anunciante;
import br.unisinos.model.Anuncio;
import br.unisinos.model.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Vinicius Pegorini Arnhold.
 */
public class UsuarioDAO {

    private EntityManager em;

    public UsuarioDAO(EntityManager entityManager) {
        this.em = entityManager;
    }

    public List<Usuario> listarTodosUsuarios() {

        TypedQuery<Usuario> query =
                this.em.createQuery("select u from Usuario u", Usuario.class);

        List<Usuario> resultList = query.getResultList();

        System.out.println("Todos os Usuarios: ");
        resultList.forEach(System.out::println);
        System.out.println("-------------------");

        return resultList;
    }


    public List<Usuario> listarUsuarioComInteresses(int minInteresses) {
        TypedQuery<Usuario> query =
                this.em.createQuery("select usuario from Usuario usuario where usuario.interesses.size >= :mInt", Usuario.class);

        query.setParameter("mInt", minInteresses);

        List<Usuario> usuarios = query.getResultList();

        System.out.println("Usuarios com no minimo [" + minInteresses + "] interesse");
        usuarios.forEach(System.out::println);
        System.out.println("-------------------");

        return usuarios;
    }

    /**
     * @return Lista de todos os anuncios dos anunciantes em que um usuario em no minimo um anuncio como interesse.
     */
    public Set<Anuncio> listarAnunciosRelacionados(Usuario usuario) {

        TypedQuery<Usuario> query =
                this.em.createQuery("select usuario from Usuario usuario where usuario.id = :id", Usuario.class);

        query.setParameter("id", usuario.getId());

        Set<Anuncio> anuncios = query.getSingleResult().getInteresses().stream()
                .map(Anuncio::getAnunciante)
                .distinct()
                .map(Anunciante::getAnuncios)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());


        System.out.println("Todos os Anuncios dos Anunciantes dos anuncios em que o usuario [" + usuario.getNome() + "] esta interessado: ");
        anuncios.forEach(System.out::println);
        System.out.println("-------------------");

        return anuncios;
    }

}
