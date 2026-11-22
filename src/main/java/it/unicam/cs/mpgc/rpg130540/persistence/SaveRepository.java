package it.unicam.cs.mpgc.rpg130540.persistence;

import java.io.IOException;

/**
 * L'interfaccia SaveRepository definisce il contratto di memorizzazione e recupero dei dati di una partita,
 * senza imporre formato o supporto di archiviazione.
 */
public interface SaveRepository {
    /**
     * Memorizza i dati forniti.
     *
     * @param data dati da conservare, non nulli
     * @throws IOException se la scrittura o la serializzazione falliscono
     */
    void save(SaveData data) throws IOException;

    /**
     * Recupera i dati memorizzati senza ricostruire la partita.
     *
     * @return dati recuperati, da validare prima della ricostruzione
     * @throws IOException se il file è assente, non leggibile o non convertibile
     *                     nel formato richiesto
     */
    SaveData load() throws IOException;
}