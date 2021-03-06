package br.unisinos.marshal;

import java.io.OutputStream;

public interface Marshaller {

    void marshal(Object o, OutputStream out) throws Exception;

    Object getExtensionName();

    default String fileNameFor(Object obj, String suffix) {
        return String.format("%s-%s.%s", obj.getClass().getSimpleName(), suffix, getExtensionName());
    }
}
