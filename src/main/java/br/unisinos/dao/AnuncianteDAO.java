package br.unisinos.dao;

import br.unisinos.model.Anunciante;
import br.unisinos.model.Anuncio;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Set;

public class AnuncianteDAO {

    @PersistenceContext
    private EntityManager em;

    public Set<Anuncio> listarAnuncios(Anunciante anunciante, int tamanho) {
        TypedQuery<Anunciante> query =
                this.em.createQuery(
                        "select anunciante " +
                                "from Anunciante anunciante " +
                                "where anunciante.id = :aId", Anunciante.class);
        query.setParameter("aId", anunciante.getId());

        Anunciante resultList = query.setMaxResults(tamanho).getSingleResult();

        System.out.println("Todos os anuncios do Anunciante: " + anunciante.getNome());

        resultList.getAnuncios().forEach(System.out::println);

        System.out.println("-----------------------------");

        return resultList.getAnuncios();

    }
}
