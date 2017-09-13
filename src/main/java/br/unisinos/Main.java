package br.unisinos;

import br.unisinos.dao.AnuncianteDAO;
import br.unisinos.dao.AnuncioDAO;
import br.unisinos.dao.UsuarioDAO;
import br.unisinos.marshal.JAXBMarshaller;
import br.unisinos.marshal.JacksonMarshaller;
import br.unisinos.marshal.Marshaller;
import br.unisinos.model.Anunciante;
import br.unisinos.model.Anuncio;
import br.unisinos.model.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
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

        factory.close();
    }

    private void doRun() throws Exception {

        generateSchemas(Anunciante.class, Anuncio.class, Usuario.class);

        adicionarDados();

        //Query
        AnuncianteDAO anuncianteDAO = new AnuncianteDAO(this.entityManager);
        UsuarioDAO usuarioDAO = new UsuarioDAO(this.entityManager);

        List<Usuario> usuarios = usuarioDAO.listarTodosUsuarios();

        List<Anuncio> anuncios = new AnuncioDAO(this.entityManager).listarTodosAnuncios();

        List<Anunciante> anunciantes = anuncianteDAO.listarTodosAnunciantes();

        anuncios.forEach(anuncio -> anuncianteDAO.listarAnunciosDoAnunciante(anuncio.getAnunciante(), Integer.MAX_VALUE));

        List<Usuario> usuariosComAoMenosUmInteresse = usuarioDAO.listarUsuarioComInteresses(1);

        usuariosComAoMenosUmInteresse.forEach(usuarioDAO::listarAnunciosRelacionados);

        //Marshall
        JAXBMarshaller xmlMarshaller = new JAXBMarshaller(Anuncio.class, Anunciante.class, Usuario.class);
        JacksonMarshaller jsonMarshaller = new JacksonMarshaller();

        marshalAll(usuarios, xmlMarshaller);
        marshalAll(usuarios, jsonMarshaller);

        marshalAll(anunciantes, xmlMarshaller);
        marshalAll(anunciantes, jsonMarshaller);

        marshalAll(anuncios, xmlMarshaller);
        marshalAll(anuncios, jsonMarshaller);
    }

    private void generateSchemas(Class<?>... clz) throws JAXBException, IOException {
        JAXBContext.newInstance(clz).generateSchema(new SchemaOutputResolver() {
            @Override
            public Result createOutput(String namespaceUri, String suggestedFileName) throws IOException {
                File file = new File(Main.this.rootExport, String.format("schemas/%s/", suggestedFileName));
                file.getParentFile().mkdirs();
                StreamResult result = new StreamResult(file);
                result.setSystemId(file.toURI().toURL().toString());
                return result;
            }
        });
    }


    private <T> void marshalAll(Iterable<T> objs, Marshaller marshaller) throws Exception {

        int count = 1;
        for (T obj : objs) {
            File file = new File(this.rootExport, String.format("/marshall/%s/%s/%s",
                    marshaller.getExtensionName(),
                    obj.getClass().getSimpleName(),
                    marshaller.fileNameFor(obj, Integer.toString(count++))));

            file.getParentFile().mkdirs();
            file.createNewFile();
            marshaller.marshal(obj, new FileOutputStream(file));
        }

    }

    private void adicionarDados() {

        this.entityManager.getTransaction().begin();
        try {
            Anunciante anunciante = new Anunciante()
                    .setNome("Carlos Alberto")
                    .setTelefone("telefone como string")
                    .setEmail("contato@profissional.aol")
                    .setNomeFantasia("Loja de Ferramentas")
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
                    .setDataPublicacao(new Date())
                    .setTitulo("Venda de Enchadas")
                    .setDescricao("Anuncio de Enchada do Carlos")
                    .setDisponivel(true)
                    .setValor(12.34);

            anunciante.addAnuncio(anuncioDeFerramentas);

            interessado1.addInteresse(anuncioDeFerramentas);

            this.entityManager.persist(anunciante);

            this.entityManager.persist(interessado1);

            this.entityManager.persist(interessado2);

            this.entityManager.getTransaction().commit();


        } catch (Throwable e) {
            this.entityManager.getTransaction().rollback();
            throw e;
        }

    }
}
