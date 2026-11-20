package it.unicam.cs.mpgc.rpg130540.game;

import it.unicam.cs.mpgc.rpg130540.model.enemies.Enemy;
import it.unicam.cs.mpgc.rpg130540.model.enemies.SimpleGuard;
import it.unicam.cs.mpgc.rpg130540.model.enemies.StrongGuard;
import it.unicam.cs.mpgc.rpg130540.model.enemies.Bodyguard;
import it.unicam.cs.mpgc.rpg130540.model.enemies.Mage;
import it.unicam.cs.mpgc.rpg130540.model.enemies.King;

/**
 * Classe EncounterFactory che crea un nuovo incontro per uno dei cinque livelli previsti.
 * Ogni richiesta valida costruisce un nuovo nemico e assegna un bottino di dieci monete.
 */
public class EncounterFactory {

    /**
     * Ultimo livello disponibile nella progressione.
     */
    private static final int LAST_LEVEL = 5;

    /**
     * Crea il nemico del livello richiesto e un incontro con dieci monete di bottino.
     *
     * @param level livello da creare, compreso tra uno e cinque
     * @return nuovo incontro con un nuovo nemico
     * @throws IllegalArgumentException se il livello non è disponibile
     */
    public Encounter createEncounter(int level) {
        Enemy enemy;
        switch (level) {
            case 1:
                enemy = new SimpleGuard();
                break;
            case 2:
                enemy = new StrongGuard();
                break;
            case 3:
                enemy = new Bodyguard();
                break;
            case 4:
                enemy = new Mage();
                break;
            case 5:
                enemy = new King();
                break;
            default:
                throw new IllegalArgumentException("Livello non disponibile");
        }
        return new Encounter(enemy, 10);
    }

    /**
     * Restituisce il numero dell’ultimo livello disponibile.
     *
     * @return 5
     */
    public int getLastLevel() {
        return LAST_LEVEL;
    }
}