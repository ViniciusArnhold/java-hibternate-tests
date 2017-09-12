package br.unisinos.marshal;

import java.io.OutputStream;

public interface Marshaller {

    void marshal(Object o, OutputStream out) throws Exception;

    Object getExtensionName();

    default String fileNameFor(Object obj, int count) {
        return String.format("%s-%d.%s", obj.getClass().getSimpleName(), count, getExtensionName());
    }
}
