package it.unicam.cs.mpgc.rpg130540.persistence;

import it.unicam.cs.mpgc.rpg130540.game.GameSession;
import it.unicam.cs.mpgc.rpg130540.items.Artifact;
import it.unicam.cs.mpgc.rpg130540.items.HealingPotion;
import it.unicam.cs.mpgc.rpg130540.items.StrengthPotion;
import it.unicam.cs.mpgc.rpg130540.items.Blowpipe;
import it.unicam.cs.mpgc.rpg130540.items.WildHealingPlant;

import java.util.ArrayList;

/**
 * Classe GameSaver che estrae dalla sessione i dati necessari al salvataggio. Converte eroe e manufatti nei
 * contenitori di persistenza, senza produrre JSON o scrivere file.
 */
public class GameSaver {

    /**
     * Estrae statistiche, inventario e progressione dalla sessione in un momento salvabile. Non
     * scrive file e non converte i dati in JSON.
     *
     * @param session sessione della partita, non nulla
     * @return nuovo contenitore dei dati della partita
     * @throws IllegalStateException se la sessione è nulla o non è tra incontri dopo la ricompensa
     * @throws IllegalArgumentException se eroe o manufatti non sono supportati dal salvataggio
     */
    public SaveData toSaveData(GameSession session) {
        if (session == null || !session.canSave()) {
            throw new IllegalStateException("Puoi salvare tra gli incontri, dopo la ricompensa");
        }
        HeroSave hero = new HeroSave();
        hero.heroType = getHeroType(session.getHeroName());
        hero.lifePoints = session.getHeroLifePoints();
        hero.attack = session.getHeroAttack();
        hero.inventory = new ArrayList<>();
        for (Artifact artifact : session.getInventoryItems()) {
            hero.inventory.add(toSavedItem(artifact));
        }
        return new SaveData(hero, session.getHonor(), session.getCurrentLevel());
    }

    /**
     * Converte il nome visualizzato dell'eroe nell'identificativo stabile usato dal formato di
     * salvataggio.
     *
     * @param name nome dell'eroe da riconoscere
     * @return ROBIN_HOOD oppure LITTLE_JOHN
     * @throws IllegalArgumentException se il nome non corrisponde a un eroe supportato
     */
    private String getHeroType(String name) {
        if ("Robin Hood".equals(name)) {
            return "ROBIN_HOOD";
        }
        if ("Little John".equals(name)) {
            return "LITTLE_JOHN";
        }
        throw new IllegalArgumentException("Eroe non supportato");
    }

    /**
     * Converte uno dei quattro manufatti supportati nel relativo identificativo e nella potenza.
     *
     * @param artifact manufatto da descrivere
     * @return nuovo contenitore del manufatto
     * @throws IllegalArgumentException se il manufatto non è riconosciuto, anche quando è nullo
     */
    private ItemSave toSavedItem(Artifact artifact) {
        ItemSave item = new ItemSave();
        if (artifact instanceof HealingPotion) {
            HealingPotion potion = (HealingPotion) artifact;

            item.type = "HEALING_POTION";
            item.power = potion.getHealingPoints();
            return item;
        }
        if (artifact instanceof StrengthPotion) {
            StrengthPotion potion = (StrengthPotion) artifact;

            item.type = "STRENGTH_POTION";
            item.power = potion.getBonus();
            return item;
        }
        if (artifact instanceof Blowpipe) {
            Blowpipe blowpipe = (Blowpipe) artifact;

            item.type = "BLOWPIPE";
            item.power = blowpipe.getBonus();
            return item;
        }
        if (artifact instanceof WildHealingPlant) {
            WildHealingPlant plant = (WildHealingPlant) artifact;

            item.type = "WILD_HEALING_PLANT";
            item.power = plant.getHealingPoints();
            return item;
        }
        throw new IllegalArgumentException(
                "Oggetto non supportato dal salvataggio"
        );
    }
}