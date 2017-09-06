package br.unisinos;

import br.unisinos.dao.AnuncioDAO;
import br.unisinos.model.Anunciante;
import br.unisinos.model.Anuncio;
import br.unisinos.model.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.time.ZonedDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private Main() {

    }

    public static void main(String[] args) {
        System.out.println("Heyo");

        Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("bd2_anuncios");

        EntityManager em = factory.createEntityManager();

        em.getTransaction().begin();
        try {

            Anunciante anunciante = new Anunciante()
                    .setNome("Carlos Alberto")
                    .setTelefone("telefone como string")
                    .setEmail("contato@profissional.aol")
                    .setNomeFantasia("Loja do Ferramentas")
                    .setClassificacao(3.6D);

            Usuario interessado1 = new Usuario()
                    .setNome("Gabriela")
                    .setTelefone("123456")
                    .setEmail("not@real.ly");

            Usuario interessado2 = new Usuario()
                    .setNome("Vinicius")
                    .setTelefone("98765")
                    .setEmail("ola@hello.me");

            Anuncio anuncioDeFerramentas = new Anuncio(anunciante)
                    .setDataPublicacao(ZonedDateTime.now())
                    .setTitulo("Venda de Enchadas")
                    .setDescricao("Anuncio de Enchada do Carlos")
                    .setDisponivel(true)
                    .setValor(12.34);

            anunciante.addAnuncio(anuncioDeFerramentas);

            interessado1.addInteresse(anuncioDeFerramentas);

            em.persist(anunciante);

            em.merge(interessado1);

            em.persist(interessado2);

            em.getTransaction().commit();


        } catch (Throwable e) {
            em.getTransaction().rollback();
            throw e;
        }

        System.out.println("Todos os Anuncios: ");

        TypedQuery<Anuncio> query =
                em.createQuery("select a from Anuncio a", Anuncio.class);

        query.getResultList().forEach(System.out::println);

        System.out.println("FIM");

    }
}
