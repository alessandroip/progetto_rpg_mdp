package it.unicam.cs.mpgc.rpg130540.persistence;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.File;
import java.io.IOException;

/**
 * Legge e scrive SaveData nel file save.json usando Jackson. Il percorso è relativo alla cartella
 * di lavoro e la scrittura sostituisce direttamente il file esistente, senza file temporaneo.
 */
public class SaveManager implements SaveRepository {
    /**
     * Convertitore Jackson configurato per leggere e scrivere JSON.
     */
    private final ObjectMapper mapper;

    /**
     * Configura Jackson per rifiutare valori aggiuntivi dopo il documento, null per i primitivi
     * e conversioni da decimali a interi.
     */
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

    /**
     * Serializza i dati in JSON leggibile e sostituisce direttamente il contenuto di save.json
     * nella cartella di lavoro.
     *
     * @param data dati da conservare, non nulli
     * @throws IOException se la scrittura o la serializzazione falliscono
     * @throws IllegalArgumentException se i dati sono nulli
     */
    @Override
    public void save(SaveData data) throws IOException {
        if (data == null) {
            throw new IllegalArgumentException("I dati da salvare sono obbligatori");
        }
        this.mapper.writerWithDefaultPrettyPrinter().writeValue(new File("save.json"), data);
    }

    /**
     * Legge save.json dalla cartella di lavoro e lo converte in SaveData. Non verifica le regole
     * del gioco; un documento JSON null può produrre un risultato nullo, che il servizio deve
     * rifiutare.
     *
     * @return dati recuperati, da validare prima della ricostruzione
     * @throws IOException se il file è assente, non leggibile o non convertibile nel formato richiesto
     */
    @Override
    public SaveData load() throws IOException {
        return this.mapper.readValue(new File("save.json"), SaveData.class);
    }
}