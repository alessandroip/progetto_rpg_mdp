package it.unicam.cs.mpgc.rpg130540.game;

import it.unicam.cs.mpgc.rpg130540.model.enemies.Enemy;
import it.unicam.cs.mpgc.rpg130540.model.enemies.SimpleGuard;
import it.unicam.cs.mpgc.rpg130540.model.enemies.StrongGuard;
import it.unicam.cs.mpgc.rpg130540.model.enemies.Bodyguard;
import it.unicam.cs.mpgc.rpg130540.model.enemies.Mage;
import it.unicam.cs.mpgc.rpg130540.model.enemies.King;

public class EncounterFactory {

    private static final int LAST_LEVEL = 5;

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

    public int getLastLevel() {
        return LAST_LEVEL;
    }
}