package it.unicam.cs.mpgc.rpg130540.persistence;

import it.unicam.cs.mpgc.rpg130540.game.GameSession;

import java.io.IOException;

/**
 * Coordina coordina conversione, validazione e accesso al salvataggio. Durante il caricamento
 * valida i dati prima di ricostruire una nuova sessione.
 */
public class GameSaveService {
    /**
     * Componente di memorizzazione dei dati.
     */
    private final SaveRepository repository;
    /**
     * Controllore della coerenza dei dati.
     */
    private final SaveDataValidator validator;
    /**
     * Preparatore dei dati a partire dalla sessione.
     */
    private final GameSaver saver;
    /**
     * Ricostruttore delle sessioni dai dati validati.
     */
    private final SaveLoader loader;

    /**
     * Il costruttore collega i componenti che preparano, verificano, memorizzano e ricostruiscono i dati.
     *
     * @param repository componente di lettura e scrittura, non nullo
     * @param validator validatore dei dati, non nullo
     * @param saver convertitore dalla sessione ai dati, non nullo
     * @param loader ricostruttore della sessione, non nullo
     * @throws IllegalArgumentException se almeno un componente è nullo
     */
    public GameSaveService(SaveRepository repository, SaveDataValidator validator, GameSaver saver, SaveLoader loader) {
        if (repository == null || validator == null || saver == null || loader == null) {
            throw new IllegalArgumentException("I componenti della persistenza sono obbligatori");
        }
        this.repository = repository;
        this.validator = validator;
        this.saver = saver;
        this.loader = loader;
    }

    /**
     * Converte la sessione in dati, li valida e li passa al repository. Non scrive se
     * preparazione o validazione falliscono.
     *
     * @param session sessione della partita, non nulla
     * @throws IOException se il repository non riesce a memorizzare i dati
     * @throws IllegalStateException se la sessione non è salvabile
     * @throws IllegalArgumentException se i dati non sono validi o contengono tipi non supportati
     */
    public void save(GameSession session) throws IOException {
        SaveData data = this.saver.toSaveData(session);
        this.validator.validate(data);
        this.repository.save(data);
    }
    public GameSession load() throws IOException {
        SaveData data = this.repository.load();

        try {
            this.validator.validate(data);
            return this.loader.toGameSession(data);

        } catch (IllegalArgumentException | IllegalStateException exception) {
            throw new IOException("Il salvataggio non rappresenta una partita valida", exception);
        }
    }
}