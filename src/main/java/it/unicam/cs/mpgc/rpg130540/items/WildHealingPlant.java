package it.unicam.cs.mpgc.rpg130540.items;

import it.unicam.cs.mpgc.rpg130540.model.heroes.Hero;

/**
 * Manufatto consumabile che recupera punti vita.
 */
public class WildHealingPlant implements Artifact {
    private final int healingPoints;

    public WildHealingPlant(int healingPoints) {
        if (healingPoints <= 0) {
            throw new IllegalArgumentException("La cura deve essere positiva");
        }
        this.healingPoints = healingPoints;
    }

    @Override
    public String getName() {
        return "Pianta selvatica (+" + this.healingPoints + " PV)";
    }

    @Override
    public boolean use(Hero hero) {
        if (!hero.isAlive() || hero.getLifePoints() == hero.getLifePointsMax()) {
            return false;
        }
        hero.heal(this.healingPoints);
        return true;
    }

    public int getHealingPoints() {
        return this.healingPoints;
    }
}
