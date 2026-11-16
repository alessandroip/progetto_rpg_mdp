package it.unicam.cs.mpgc.rpg130540.model.heroes;

import it.unicam.cs.mpgc.rpg130540.abilities.Ability;
import it.unicam.cs.mpgc.rpg130540.abilities.PreciseShot;
import it.unicam.cs.mpgc.rpg130540.abilities.defenses.Defense;
import it.unicam.cs.mpgc.rpg130540.abilities.defenses.Dodge;

/**
 * La classe RobinHood rappresenta Robin Hood, l'eroe con l'attacco base più alto.
 */
public class RobinHood extends Hero {
    public final Ability specialAbility = new PreciseShot();
    public final Defense specialDefense = new Dodge();

    /**
     * Il costruttore inizializza Robin Hood con 12 punti vita e 5 punti di attacco base.
     */
    public RobinHood() {
        super("Robin Hood", 12, 5);
    }

    @Override
    public Ability getSpecialAbility() {
        return this.specialAbility;
    }

    @Override
    public Defense getSpecialDefense() {
        return this.specialDefense;
    }
}
