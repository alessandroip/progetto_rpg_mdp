package it.unicam.cs.mpgc.rpg130540.model.heroes;

import it.unicam.cs.mpgc.rpg130540.model.Entity;
import it.unicam.cs.mpgc.rpg130540.abilities.Ability;
import it.unicam.cs.mpgc.rpg130540.abilities.defenses.Defense;
import it.unicam.cs.mpgc.rpg130540.items.InventoryManager;

/**
 * Classe Hero (sottoclasse di Entity) che rappresenta tutto ciò che gli eroi condividono
 * oltre alle caratteristiche di Entity.
 */
public abstract class Hero extends Entity {
    /**
     * Ogni eroe possiede il proprio inventario, inizialmente vuoto.
     */
    private final InventoryManager inventory = new InventoryManager();

    /**
     * Il costruttore crea un eroe con i punti vita al massimo.
     *
     * @param name nome dell'eroe
     * @param lifePointsMax punti vita massimi, maggiori di zero
     * @param attack valore dell'attacco base, maggiore di zero
     * @throws IllegalArgumentException se il nome è nullo o privo di caratteri
     *          visibili oppure se vita massima o attacco sono minori o uguali
     *          a zero
     */
    public Hero(String name, int lifePointsMax, int attack) {
        super(name, lifePointsMax, attack);
    }

    /**
     * Fornisce l'abilità offensiva specifica dell'eroe. La disponibilità per incontro
     * viene controllata da Fight.
     *
     * @return abilità speciale dell'eroe, non nulla
     */
    public abstract Ability getSpecialAbility();

    /**
     * Fornisce la difesa specifica dell'eroe. La disponibilità per incontro viene
     * controllata da Fight.
     *
     * @return difesa speciale dell'eroe, non nulla
     */
    public abstract Defense getSpecialDefense();

    /**
     * Restituisce l'inventario personale modificabile, non una sua copia.
     *
     * @return gestore dell'inventario dell'eroe
     */
    public InventoryManager getInventory() {
        return this.inventory;
    }

    /**
     * Aumenta permanentemente l'attacco corrente. Usa una somma controllata
     * per evitare il superamento del limite numerico di int.
     *
     * @param bonus incremento positivo dell'attacco
     * @throws IllegalArgumentException se il bonus non è positivo
     * @throws ArithmeticException se la somma supera il limite di int
     */
    public void increaseAttack(int bonus) {
        if (bonus <= 0) {
            throw new IllegalArgumentException("Il bonus deve essere positivo");
        }
        this.attack = Math.addExact(this.attack, bonus);
    }

    /**
     * Ripristina i valori validi di punti vita e attacco dell'eroe. Non modifica inventario,
     * abilità o difesa.
     *
     * Questo metodo serve perché un eroe appena creato ha le statistiche iniziali, mentre quello
     * caricato deve recuperare i valori registrati.
     *
     * @param savedLifePoints punti vita da ripristinare, maggiori di zero e non superiori al massimo
     * @param savedAttack attacco da ripristinare, maggiore di zero
     * @throws IllegalArgumentException se uno dei valori non è valido
     */
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