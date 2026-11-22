package it.unicam.cs.mpgc.rpg130540.items;

/**
 * Classe StrengthPotion che aumenta permanentemente l'attacco dell'eroe.
 */
public class StrengthPotion extends AttackBoostArtifact {

    /**
     * Il costruttore crea una pozione di forza.
     *
     * @param bonus incremento permanente di attacco, maggiore di zero
     * @throws IllegalArgumentException se il bonus non è positivo
     */
    public StrengthPotion(int bonus) {
        super(bonus);
    }

    @Override
    public String getName() {
        return "Pozione di forza (+" + getBonus() + " attacco)";
    }
}