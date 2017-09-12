package br.unisinos;

import br.unisinos.marshal.JAXBMarshaller;
import br.unisinos.marshal.JacksonMarshaller;
import br.unisinos.marshal.Marshaller;
import br.unisinos.model.Anunciante;
import br.unisinos.model.Anuncio;
import br.unisinos.model.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.io.File;
import java.io.FileOutputStream;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private final EntityManager entityManager;
    private final File rootExport;

    private Main(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.rootExport = new File("./export/");
    }

    public static void main(String[] args) throws Exception {
        Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("bd2_anuncios");

        Main instance = new Main(factory.createEntityManager());

        instance.doRun();
    }

    private void doRun() throws Exception {

        adicionarDados();

        List<Anuncio> anuncios = listarTodosAnuncios();

        anuncios.forEach(anuncio -> listarAnuncios(anuncio.getAnunciante(), Integer.MAX_VALUE));

        marshalXML(anuncios);

        //marshalJSON(anuncios);
    }

    private void marshalJSON(List<Anuncio> anuncios) throws Exception {
        Marshaller marshaller = new JacksonMarshaller();

        for (Anuncio anuncio : anuncios) {
            File file = new File(rootExport, "json/anuncios/anuncio-" + anuncio.getId() + ".json");
            file.getParentFile().mkdirs();
            file.createNewFile();
            marshaller.marshal(anuncio, new FileOutputStream(file));
        }
    }

    private void marshalXML(List<Anuncio> anuncios) throws Exception {

        Marshaller marshaller = new JAXBMarshaller(Anunciante.class, Anuncio.class, Usuario.class);

        for (Anuncio anuncio : anuncios) {
            File file = new File(rootExport, "xml/anuncios/anuncio-" + anuncio.getId() + ".xml");
            file.getParentFile().mkdirs();
            file.createNewFile();
            marshaller.marshal(anuncio, new FileOutputStream(file));
        }
    }

    private Set<Anuncio> listarAnuncios(Anunciante anunciante, int tamanho) {
        TypedQuery<Anunciante> query =
                this.entityManager.createQuery("" +
                        "select anunciante " +
                        "from Anunciante anunciante " +
                        "where anunciante.id = :aId", Anunciante.class);
        query.setParameter("aId", anunciante.getId());

        Anunciante resultList = query.setMaxResults(tamanho).getSingleResult();

        System.out.println("Todos os anuncios do Anunciante: " + anunciante);

        resultList.getAnuncios().forEach(System.out::println);

        System.out.println("-----------------------------");

        return resultList.getAnuncios();

    }

    private void adicionarDados() {

        entityManager.getTransaction().begin();
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

            entityManager.persist(anunciante);

            entityManager.merge(interessado1);

            entityManager.persist(interessado2);

            entityManager.getTransaction().commit();


        } catch (Throwable e) {
            entityManager.getTransaction().rollback();
            throw e;
        }

    }

    public List<Anuncio> listarTodosAnuncios() {
        System.out.println("Todos os Anuncios: ");

        TypedQuery<Anuncio> query =
                entityManager.createQuery("select a from Anuncio a", Anuncio.class);

        List<Anuncio> resultList = query.getResultList();

        System.out.println("Anuncios no banco: ");
        resultList.forEach(System.out::println);
        System.out.println("-------------------");

        return resultList;
    }
}
