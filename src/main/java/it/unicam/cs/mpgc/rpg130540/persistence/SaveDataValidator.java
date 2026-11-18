package it.unicam.cs.mpgc.rpg130540.persistence;

import it.unicam.cs.mpgc.rpg130540.game.EncounterFactory;
import it.unicam.cs.mpgc.rpg130540.model.heroes.Hero;
import it.unicam.cs.mpgc.rpg130540.model.heroes.LittleJohn;
import it.unicam.cs.mpgc.rpg130540.model.heroes.RobinHood;

import java.util.List;

/**
 * Verifica che i dati salvati siano coerenti con le regole della partita prima della ricostruzione.
 */
public class SaveDataValidator {

    private final EncounterFactory encounterFactory;

    public SaveDataValidator(EncounterFactory encounterFactory) {
        if (encounterFactory == null) {
            throw new IllegalArgumentException(
                    "La factory è obbligatoria"
            );
        }

        this.encounterFactory = encounterFactory;
    }

    public void validate(SaveData data) {
        if (data == null) {
            throw new IllegalArgumentException(
                    "Salvataggio mancante"
            );
        }

        if (data.getVersion() != 1) {
            throw new IllegalArgumentException(
                    "Versione del salvataggio non supportata"
            );
        }

        HeroSave savedHero = data.getHero();

        if (savedHero == null) {
            throw new IllegalArgumentException(
                    "Dati dell'eroe mancanti"
            );
        }

        Hero referenceHero =
                createReferenceHero(savedHero.heroType);

        if (savedHero.lifePoints <= 0
                || savedHero.lifePoints
                > referenceHero.getLifePointsMax()) {
            throw new IllegalArgumentException(
                    "Punti vita salvati non validi"
            );
        }

        if (savedHero.attack < referenceHero.getAttack()) {
            throw new IllegalArgumentException(
                    "Attacco salvato inferiore all'attacco iniziale"
            );
        }

        int completedLevel = data.getCompletedLevel();

        if (completedLevel < 1
                || completedLevel
                >= this.encounterFactory.getLastLevel()) {
            throw new IllegalArgumentException(
                    "Il livello salvato deve essere tra due incontri"
            );
        }

        if (data.getHonor() != completedLevel) {
            throw new IllegalArgumentException(
                    "Onore e livello completato non sono coerenti"
            );
        }

        List<ItemSave> inventory = savedHero.inventory;

        if (inventory == null) {
            throw new IllegalArgumentException(
                    "Inventario mancante"
            );
        }

        for (ItemSave item : inventory) {
            validateItem(item);
        }
    }

    private Hero createReferenceHero(String type) {
        if ("ROBIN_HOOD".equals(type)) {
            return new RobinHood();
        }

        if ("LITTLE_JOHN".equals(type)) {
            return new LittleJohn();
        }

        throw new IllegalArgumentException(
                "Eroe non riconosciuto"
        );
    }

    private void validateItem(ItemSave item) {
        if (item == null) {
            throw new IllegalArgumentException(
                    "L'inventario contiene un oggetto nullo"
            );
        }

        if (!"HEALING_POTION".equals(item.type)
                && !"STRENGTH_POTION".equals(item.type)) {
            throw new IllegalArgumentException(
                    "Tipo di oggetto non riconosciuto"
            );
        }

        if (item.power <= 0) {
            throw new IllegalArgumentException(
                    "La potenza dell'oggetto deve essere positiva"
            );
        }
    }
}