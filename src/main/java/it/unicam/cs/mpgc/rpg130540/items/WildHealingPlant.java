package it.unicam.cs.mpgc.rpg130540.items;

/**
 * Classe WildHealingPlant che recupera punti vita fino al massimo.
 */
public class WildHealingPlant extends HealingArtifact {

    /**
     * Il costruttore crea una pianta selvatica.
     *
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