package br.unisinos.dao;

import br.unisinos.model.Anuncio;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class AnuncioDAO {

    @PersistenceContext
    private EntityManager em;

    public List<Anuncio> selectTodosAnuncios() {
        TypedQuery<Anuncio> query =
                em.createQuery("select a from Anuncio a", Anuncio.class);

        return query.getResultList();
    }
}
