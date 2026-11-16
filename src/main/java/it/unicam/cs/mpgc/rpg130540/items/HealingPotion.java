package it.unicam.cs.mpgc.rpg130540.items;

import it.unicam.cs.mpgc.rpg130540.model.heroes.Hero;

public class HealingPotion implements Artifact {

    private final int healingPoints;

    public HealingPotion(int healingPoints) {
        if (healingPoints <= 0) {
            throw new IllegalArgumentException("La cura deve essere positiva");
        }

        this.healingPoints = healingPoints;
    }

    @Override
    public String getName() {
        return "Pozione curativa (+" + this.healingPoints + " PV)";
    }

    @Override
    public boolean use(Hero hero) {
        if (!hero.isAlive() || hero.getLifePoints() == hero.getLifePointsMax()) {
            return false;
        }

        hero.heal(this.healingPoints);
        return true;
    }
}