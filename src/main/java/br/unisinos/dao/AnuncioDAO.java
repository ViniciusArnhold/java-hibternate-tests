package br.unisinos.dao;

import br.unisinos.model.Anuncio;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class AnuncioDAO {

    private EntityManager em;

    public AnuncioDAO(EntityManager entityManager) {
        this.em = entityManager;
    }

    public List<Anuncio> listarTodosAnuncios() {

        TypedQuery<Anuncio> query =
                this.em.createQuery("select a from Anuncio a", Anuncio.class);

        List<Anuncio> resultList = query.getResultList();

        System.out.println("Todos os Anuncios: ");
        resultList.forEach(System.out::println);
        System.out.println("-------------------");

        return resultList;
    }
}
