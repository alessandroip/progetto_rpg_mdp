package it.unicam.cs.mpgc.rpg130540.model.heroes;

import it.unicam.cs.mpgc.rpg130540.model.Entity;
import it.unicam.cs.mpgc.rpg130540.abilities.Ability;
import it.unicam.cs.mpgc.rpg130540.abilities.defenses.Defense;
import it.unicam.cs.mpgc.rpg130540.items.InventoryManager;

/**
 * Classe Hero (sottoclasse di Entity) che rappresenta le entità protagoniste del gioco.
 */
public abstract class Hero extends Entity {
    private final InventoryManager inventory = new InventoryManager();

    /**
     * Il costruttore della classe Hero crea un eroe con i punti vita al massimo.
     *
     * @param name nome dell'eroe
     * @param lifePointsMax punti vita massimi, maggiori di zero
     * @param attack valore dell'attacco base, maggiore di zero
     * @throws IllegalArgumentException se il nome è nullo o privo di caratteri
     *          visibili, oppure se vita massima o attacco sono minori o uguali
     *          a zero
     */
    public Hero(String name, int lifePointsMax, int attack) {
        super(name, lifePointsMax, attack);
    }

    public abstract Ability getSpecialAbility();

    public abstract Defense getSpecialDefense();

    public InventoryManager getInventory() {
        return this.inventory;
    }

    public void increaseAttack(int bonus) {
        if (bonus <= 0) {
            throw new IllegalArgumentException("Il bonus deve essere positivo");
        }

        this.attack = Math.addExact(this.attack, bonus);
    }

    public void restoreCombatStats(int savedLifePoints, int savedAttack) {
        if (savedLifePoints <= 0 || savedLifePoints > this.lifePointsMax) {
            throw new IllegalArgumentException("Punti vita salvati non validi");
        }

        if (savedAttack <= 0) {
            throw new IllegalArgumentException("Attacco salvato non valido");
        }

        this.lifePoints = savedLifePoints;
        this.attack = savedAttack;
    }
}
