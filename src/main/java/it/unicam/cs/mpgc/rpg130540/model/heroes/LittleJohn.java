package it.unicam.cs.mpgc.rpg130540.model.heroes;

import it.unicam.cs.mpgc.rpg130540.abilities.Ability;
import it.unicam.cs.mpgc.rpg130540.abilities.StunningBlow;
import it.unicam.cs.mpgc.rpg130540.abilities.defenses.Defense;
import it.unicam.cs.mpgc.rpg130540.abilities.defenses.Parry;

/**
 * La classe LittleJohn configura l'eroe con più punti vita. Little John ha
 * 18 punti vita, attacco base 3, colpo stordente e parata.
 *
 * La classe configura i comportamenti, ma non decide quando possono essere usati.
 * La disponibilità di abilità e difesa durante un incontro è gestita da Fight.
 */
public class LittleJohn extends Hero {
    /**
     * Comportamento dell'abilità speciale.
     */
    private final Ability specialAbility = new StunningBlow();
    /**
     * Comportamento della difesa speciale.
     */
    private final Defense specialDefense = new Parry();

    /**
     * Il costruttore inizializza Little John con 18 punti vita e 3 punti di attacco base.
     */
    public LittleJohn() {
        super("Little John", 18, 3);
    }

    /**
     * Restituisce l'abilità speciale di Little John.
     *
     * @return in questo caso il colpo stordente (StunningBlow)
     */
    @Override
    public Ability getSpecialAbility() {
        return this.specialAbility;
    }

    /**
     * Restituisce la difesa speciale di Little John.
     *
     * @return in questo caso la parata (Parry)
     */
    @Override
    public Defense getSpecialDefense() {
        return this.specialDefense;
    }
}