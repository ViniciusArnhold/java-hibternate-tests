package br.unisinos.marshal;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.validation.Schema;
import java.io.OutputStream;

public class JAXBMarshaller implements Marshaller {

    private final JAXBContext context;
    private final javax.xml.bind.Marshaller marshaller;
    private final Unmarshaller unmarshaller;

    public JAXBMarshaller(Schema schema, Class<?>... recognizedClasses) throws JAXBException {
        this.context = JAXBContext.newInstance(recognizedClasses);
        this.marshaller = this.context.createMarshaller();
        this.marshaller.setEventHandler(event -> event.getSeverity() < ValidationEvent.ERROR);
        this.marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, true);
        this.unmarshaller = this.context.createUnmarshaller();
    }

    @Override
    public void marshal(Object o, OutputStream out) throws JAXBException {
        this.marshaller.marshal(o, out);
    }

    @Override
    public Object getExtensionName() {
        return "xml";
    }
}
