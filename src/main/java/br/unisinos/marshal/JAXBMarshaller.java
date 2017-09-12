package br.unisinos.marshal;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.OutputStream;

public class JAXBMarshaller implements Marshaller {

    private final JAXBContext context;
    private final javax.xml.bind.Marshaller marshaller;
    private final Unmarshaller unmarshaller;

    public JAXBMarshaller(Class<?>... recognizedClasses) throws JAXBException {
        this.context = JAXBContext.newInstance(recognizedClasses);
        this.marshaller = this.context.createMarshaller();
        this.unmarshaller = this.context.createUnmarshaller();
    }

    @Override
    public void marshal(Object o, OutputStream out) throws JAXBException {
        this.marshaller.marshal(o,out);
    }
}
