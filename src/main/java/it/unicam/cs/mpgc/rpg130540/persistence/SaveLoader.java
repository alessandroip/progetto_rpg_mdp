package it.unicam.cs.mpgc.rpg130540.persistence;

import it.unicam.cs.mpgc.rpg130540.game.EncounterFactory;
import it.unicam.cs.mpgc.rpg130540.game.GameSession;
import it.unicam.cs.mpgc.rpg130540.items.Artifact;
import it.unicam.cs.mpgc.rpg130540.items.HealingPotion;
import it.unicam.cs.mpgc.rpg130540.items.StrengthPotion;
import it.unicam.cs.mpgc.rpg130540.model.heroes.Hero;
import it.unicam.cs.mpgc.rpg130540.model.heroes.LittleJohn;
import it.unicam.cs.mpgc.rpg130540.model.heroes.RobinHood;
import it.unicam.cs.mpgc.rpg130540.items.Blowpipe;
import it.unicam.cs.mpgc.rpg130540.items.WildHealingPlant;

/**
 * Ricostruisce eroe, inventario e sessione a partire da dati già validati.
 * Non legge file e non interpreta direttamente il JSON.
 */
public class SaveLoader {
    /**
     * Factory degli incontri.
     */
    private final EncounterFactory encounterFactory;

    /**
     * Configura la ricostruzione delle sessioni con la factory ricevuta.
     *
     * @param encounterFactory factory usata per creare gli incontri, non nulla
     * @throws IllegalArgumentException se la factory è nulla
     */
    public SaveLoader(EncounterFactory encounterFactory) {
        if (encounterFactory == null) {
            throw new IllegalArgumentException("La factory è obbligatoria");
        }
        this.encounterFactory = encounterFactory;
    }

    /**
     * Ricrea l'eroe, ripristina statistiche e inventario e costruisce una sessione
     * tra incontri. Presuppone che SaveDataValidator abbia già verificato i dati e
     * non riassegna le ricompense degli incontri completati.
     *
     * @param data dati completi e già validati della partita
     * @return nuova sessione pronta per il livello successivo
     * @throws IllegalArgumentException se i dati non validati contengono tipi o
     *                                  valori non supportati
     */
    public GameSession toGameSession(SaveData data) {
        HeroSave savedHero = data.getHero();

        Hero hero = createHero(savedHero.heroType);

        hero.restoreCombatStats(savedHero.lifePoints, savedHero.attack);

        for (ItemSave item : savedHero.inventory) {
            hero.getInventory().add(createArtifact(item));
        }

        return new GameSession(hero, this.encounterFactory, data.getCompletedLevel(), data.getHonor());
    }

    /**
     * Crea un nuovo eroe con le sue caratteristiche iniziali e i comportamenti fissi.
     *
     * @param type identificativo dell'eroe
     * @return nuova istanza di Robin Hood o Little John
     * @throws IllegalArgumentException se il tipo non è riconosciuto
     */
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

    /**
     * Ricrea un manufatto con il tipo e la potenza salvati.
     *
     * @param item dati non nulli e già validati del manufatto
     * @return nuovo manufatto
     * @throws IllegalArgumentException se il tipo non è supportato o la potenza non è positiva
     */
    private Artifact createArtifact(ItemSave item) {
        switch (item.type) {
            case "HEALING_POTION":
                return new HealingPotion(item.power);
            case "STRENGTH_POTION":
                return new StrengthPotion(item.power);
            case "BLOWPIPE":
                return new Blowpipe(item.power);
            case "WILD_HEALING_PLANT":
                return new WildHealingPlant(item.power);
            default:
                throw new IllegalArgumentException("Oggetto non riconosciuto");
        }
    }
}