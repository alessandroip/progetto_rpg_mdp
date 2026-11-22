package it.unicam.cs.mpgc.rpg130540.items;

/**
 * Pianta selvatica: recupera punti vita fino al massimo.
 */
public class WildHealingPlant extends HealingArtifact {

    /**
     * @param healingPoints punti vita massimi recuperabili, maggiore di zero
     * @throws IllegalArgumentException se la cura non è positiva
     */
    public WildHealingPlant(int healingPoints) {
        super(healingPoints);
    }

    @Override
    public String getName() {
        return "Pianta selvatica (+" + getHealingPoints() + " PV)";
    }
}