package br.unisinos.dao;

import br.unisinos.model.Anunciante;
import br.unisinos.model.Anuncio;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AnuncianteDAO {

    private EntityManager em;

    public AnuncianteDAO(EntityManager entityManager) {
        this.em = entityManager;
    }

    public Set<Anuncio> listarAnunciosDoAnunciante(Anunciante anunciante, int tamanho) {
        TypedQuery<Anunciante> query =
                this.em.createQuery(
                        "select anunciante " +
                                "from Anunciante anunciante " +
                                "where anunciante.id = :aId", Anunciante.class);
        query.setParameter("aId", anunciante.getId());

        Anunciante resultList = query.getSingleResult();

        Set<Anuncio> result = resultList.getAnuncios().stream().limit(tamanho).collect(Collectors.toSet());

        System.out.println("Todos os anuncios do Anunciante: " + anunciante.getNome());

        result.forEach(System.out::println);

        System.out.println("-----------------------------");

        return result;
    }

    public List<Anunciante> listarTodosAnunciantes() {
        TypedQuery<Anunciante> query = this.em.createQuery(
                "select anunciante " +
                        "from Anunciante anunciante", Anunciante.class);

        List<Anunciante> resultList = query.getResultList();

        System.out.println("Todos os Anunciantes: ");
        resultList.forEach(System.out::println);
        System.out.println("-------------------");

        return resultList;
    }
}
