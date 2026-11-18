package it.unicam.cs.mpgc.rpg130540.persistence;

import it.unicam.cs.mpgc.rpg130540.game.EncounterFactory;
import it.unicam.cs.mpgc.rpg130540.game.GameSession;
import it.unicam.cs.mpgc.rpg130540.items.Artifact;
import it.unicam.cs.mpgc.rpg130540.items.HealingPotion;
import it.unicam.cs.mpgc.rpg130540.items.StrengthPotion;
import it.unicam.cs.mpgc.rpg130540.model.heroes.Hero;
import it.unicam.cs.mpgc.rpg130540.model.heroes.LittleJohn;
import it.unicam.cs.mpgc.rpg130540.model.heroes.RobinHood;

/**
 * Ricostruisce una partita dai dati già validati.
 * Non legge file e non interpreta il JSON.
 */
public class SaveLoader {
    private final EncounterFactory encounterFactory;

    public SaveLoader(EncounterFactory encounterFactory) {
        if (encounterFactory == null) {
            throw new IllegalArgumentException("La factory è obbligatoria");
        }
        this.encounterFactory = encounterFactory;
    }

    /**
     * Ricrea l'eroe, il suo inventario e la sessione.
     *
     * @param data dati della partita già validati
     * @return sessione ripristinata tra due incontri
     */
    public GameSession toGameSession(SaveData data) {
        HeroSave savedHero = data.getHero();

        Hero hero = createHero(savedHero.heroType);

        hero.restoreCombatStats(
                savedHero.lifePoints,
                savedHero.attack
        );

        for (ItemSave item : savedHero.inventory) {
            hero.getInventory().add(createArtifact(item));
        }

        return new GameSession(
                hero,
                this.encounterFactory,
                data.getCompletedLevel(),
                data.getHonor()
        );
    }

    private Hero createHero(String type) {
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

    private Artifact createArtifact(ItemSave item) {
        switch (item.type) {
            case "HEALING_POTION":
                return new HealingPotion(item.power);

            case "STRENGTH_POTION":
                return new StrengthPotion(item.power);

            default:
                throw new IllegalArgumentException(
                        "Oggetto non riconosciuto"
                );
        }
    }
}
