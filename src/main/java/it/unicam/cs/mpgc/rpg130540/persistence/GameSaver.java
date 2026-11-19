package it.unicam.cs.mpgc.rpg130540.persistence;

import it.unicam.cs.mpgc.rpg130540.game.GameSession;
import it.unicam.cs.mpgc.rpg130540.items.Artifact;
import it.unicam.cs.mpgc.rpg130540.items.HealingPotion;
import it.unicam.cs.mpgc.rpg130540.items.StrengthPotion;
import it.unicam.cs.mpgc.rpg130540.items.Blowpipe;
import it.unicam.cs.mpgc.rpg130540.items.WildHealingPlant;

import java.util.ArrayList;

/**
 * Estrae dalla sessione i dati necessari al salvataggio.
 * Non converte in JSON e non scrive file.
 */
public class GameSaver {

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

    private String getHeroType(String name) {
        if ("Robin Hood".equals(name)) {
            return "ROBIN_HOOD";
        }

        if ("Little John".equals(name)) {
            return "LITTLE_JOHN";
        }
        throw new IllegalArgumentException("Eroe non supportato");
    }

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
