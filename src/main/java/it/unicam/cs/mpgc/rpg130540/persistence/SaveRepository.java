package it.unicam.cs.mpgc.rpg130540.persistence;

import java.io.IOException;

/**
 * Questo contratto definisce le operazioni save() e load().
 */
public interface SaveRepository {
    void save(SaveData data) throws IOException;

    SaveData load() throws IOException;
}
