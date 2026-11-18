package it.unicam.cs.mpgc.rpg130540.persistence;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.File;
import java.io.IOException;

/**
 * Legge e scrive i dati della partita nel file save.json.
 */
public class SaveManager implements SaveRepository {
    private final ObjectMapper mapper;

    public SaveManager() {
        this.mapper = JsonMapper.builder()
                .enable(
                        DeserializationFeature.FAIL_ON_TRAILING_TOKENS
                )
                .enable(
                        DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES
                )
                .disable(
                        DeserializationFeature.ACCEPT_FLOAT_AS_INT
                )
                .build();
    }

    @Override
    public void save(SaveData data) throws IOException {
        if (data == null) {
            throw new IllegalArgumentException("I dati da salvare sono obbligatori");
        }
        this.mapper.writerWithDefaultPrettyPrinter().writeValue(new File("save.json"), data);
    }

    @Override
    public SaveData load() throws IOException {
        return this.mapper.readValue(new File("save.json"), SaveData.class);
    }
}
