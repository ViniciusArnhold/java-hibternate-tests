package br.unisinos.marshal;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.OutputStream;

public class JacksonMarshaller implements Marshaller {

    private final ObjectMapper objectMapper;

    public JacksonMarshaller() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void marshal(Object o, OutputStream out) throws IOException {
        this.objectMapper.writer().writeValue(out, o);

    }
}
