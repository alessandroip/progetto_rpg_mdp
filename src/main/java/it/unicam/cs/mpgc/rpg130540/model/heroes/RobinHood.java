package it.unicam.cs.mpgc.rpg130540.model.heroes;

import it.unicam.cs.mpgc.rpg130540.abilities.Ability;
import it.unicam.cs.mpgc.rpg130540.abilities.PreciseShot;
import it.unicam.cs.mpgc.rpg130540.abilities.defenses.Defense;
import it.unicam.cs.mpgc.rpg130540.abilities.defenses.Dodge;

/**
 * La classe RobinHood configura l'eroe con l'attacco base più alto. Robin Hood
 * ha 12 punti vita, attacco base 5, tiro di precisione e schivata.
 *
 * La classe configura i comportamenti, ma non decide quando possono essere usati.
 * La disponibilità di abilità e difesa durante un incontro è gestita da Fight.
 */
public class RobinHood extends Hero {
    /**
     * Comportamento dell'abilità speciale.
     */
    private final Ability specialAbility = new PreciseShot();
    /**
     * Comportamento della difesa speciale.
     */
    private final Defense specialDefense = new Dodge();

    /**
     * Il costruttore inizializza Robin Hood con 12 punti vita e 5 punti di attacco base.
     */
    public RobinHood() {
        super("Robin Hood", 12, 5);
    }

    /**
     * Restituisce l'abilità speciale di Robin Hood.
     *
     * @return in questo caso il tiro di precisione (PreciseShot)
     */
    @Override
    public Ability getSpecialAbility() {
        return this.specialAbility;
    }

    /**
     * Restituisce la difesa speciale di Robin Hood.
     *
     * @return in questo caso la schivata (Dodge)
     */
    @Override
    public Defense getSpecialDefense() {
        return this.specialDefense;
    }
}