package it.unicam.cs.mpgc.rpg130540.items;

/**
 * Classe HealingPotion che recupera punti vita fino al massimo.
 */
public class HealingPotion extends HealingArtifact {

    /**
     * Il costruttore crea la pozione curativa.
     *
     * @param healingPoints punti vita massimi recuperabili, maggiore di zero
     * @throws IllegalArgumentException se la cura non è positiva
     */
    public HealingPotion(int healingPoints) {
        super(healingPoints);
    }

    @Override
    public String getName() {
        return "Pozione curativa (+" + getHealingPoints() + " PV)";
    }
}