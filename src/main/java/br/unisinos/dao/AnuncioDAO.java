package br.unisinos.dao;

import br.unisinos.model.Anunciante;
import br.unisinos.model.Anuncio;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Set;

public class AnuncioDAO {

    @PersistenceContext
    private EntityManager em;

    public List<Anuncio> listarTodosAnuncios() {
        System.out.println("Todos os Anuncios: ");

        TypedQuery<Anuncio> query =
                em.createQuery("select a from Anuncio a", Anuncio.class);

        List<Anuncio> resultList = query.getResultList();

        System.out.println("Anuncios no banco: ");
        resultList.forEach(System.out::println);
        System.out.println("-------------------");

        return resultList;
    }
}
