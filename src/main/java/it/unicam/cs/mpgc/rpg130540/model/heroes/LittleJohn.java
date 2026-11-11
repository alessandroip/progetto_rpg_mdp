package it.unicam.cs.mpgc.rpg130540.model.heroes;

import it.unicam.cs.mpgc.rpg130540.abilities.Ability;
import it.unicam.cs.mpgc.rpg130540.abilities.StunningBlow;
import it.unicam.cs.mpgc.rpg130540.defenses.Defense;
import it.unicam.cs.mpgc.rpg130540.defenses.Parry;

/**
 * La classe LittleJohn rappresenta Little John, l'eroe con più punti vita.
 */
public class LittleJohn extends Hero {
    private Ability specialAbility = new StunningBlow();
    public Defense specialDefense = new Parry();

    /**
     * Il costruttore inizializza Little John con 18 punti vita e 3 punti di attacco base.
     */
    public LittleJohn() {
        super("Little John", 18, 3);
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
