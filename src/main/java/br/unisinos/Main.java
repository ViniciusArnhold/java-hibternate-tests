package br.unisinos;

import br.unisinos.dao.AnuncianteDAO;
import br.unisinos.dao.AnuncioDAO;
import br.unisinos.marshal.JAXBMarshaller;
import br.unisinos.marshal.JacksonMarshaller;
import br.unisinos.marshal.Marshaller;
import br.unisinos.model.Anunciante;
import br.unisinos.model.Anuncio;
import br.unisinos.model.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
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

        System.setProperty("log4j.logger.org.hibernate", "severe");

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("bd2_anuncios");

        Main instance = new Main(factory.createEntityManager());

        instance.doRun();
    }

    private void doRun() throws Exception {

        adicionarDados();

        List<Anuncio> anuncios = new AnuncioDAO().listarTodosAnuncios();

        AnuncianteDAO anuncianteDAO = new AnuncianteDAO();
        anuncios.forEach(anuncio -> anuncianteDAO.listarAnuncios(anuncio.getAnunciante(), Integer.MAX_VALUE));

        marshalAll(anuncios, new JAXBMarshaller(Anuncio.class, Anunciante.class));
        marshalAll(anuncios, new JacksonMarshaller());
    }


    private <T> void marshalAll(Iterable<T> objs, Marshaller marshaller) throws Exception {

        int count = 1;
        for (T obj : objs) {
            File file = new File(rootExport, String.format("/export/%s/%s/%s",
                    marshaller.getExtensionName(),
                    obj.getClass().getSimpleName(),
                    marshaller.fileNameFor(obj, Integer.toString(count++))));

            file.getParentFile().mkdirs();
            file.createNewFile();
            marshaller.marshal(obj, new FileOutputStream(file));
        }

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
                    .setDataPublicacao(new java.util.Date())
                    .setTitulo("Venda de Enchadas")
                    .setDescricao("Anuncio de Enchada do Carlos")
                    .setDisponivel(true)
                    .setValor(12.34);

            anunciante.addAnuncio(anuncioDeFerramentas);

            interessado1.addInteresse(anuncioDeFerramentas);

            entityManager.persist(anunciante);

            entityManager.persist(interessado1);

            entityManager.persist(interessado2);

            entityManager.getTransaction().commit();


        } catch (Throwable e) {
            entityManager.getTransaction().rollback();
            throw e;
        }

    }
}
