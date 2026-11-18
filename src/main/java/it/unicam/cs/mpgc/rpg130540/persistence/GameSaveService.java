package it.unicam.cs.mpgc.rpg130540.persistence;

import it.unicam.cs.mpgc.rpg130540.game.GameSession;

import java.io.IOException;

/**
 * Coordina preparazione, validazione, salvataggio e ricostruzione della partita.
 */
public class GameSaveService {
    private final SaveRepository repository;
    private final SaveDataValidator validator;
    private final GameSaver saver;
    private final SaveLoader loader;

    public GameSaveService(SaveRepository repository, SaveDataValidator validator, GameSaver saver, SaveLoader loader) {
        if (repository == null || validator == null || saver == null || loader == null) {
            throw new IllegalArgumentException("I componenti della persistenza sono obbligatori");
        }

        this.repository = repository;
        this.validator = validator;
        this.saver = saver;
        this.loader = loader;
    }

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
            throw new IOException(
                    "Il salvataggio non rappresenta una partita valida",
                    exception
            );
        }
    }
}