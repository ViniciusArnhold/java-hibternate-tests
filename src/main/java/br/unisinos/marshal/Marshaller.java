package br.unisinos.marshal;

import java.io.OutputStream;

public interface Marshaller {

    void marshal(Object o, OutputStream out) throws Exception;

}
