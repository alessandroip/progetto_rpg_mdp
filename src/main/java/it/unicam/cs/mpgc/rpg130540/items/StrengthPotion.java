package it.unicam.cs.mpgc.rpg130540.items;

import it.unicam.cs.mpgc.rpg130540.model.heroes.Hero;

public class StrengthPotion implements Artifact {

    private final int bonus;

    public StrengthPotion(int bonus) {
        if (bonus <= 0) {
            throw new IllegalArgumentException("Il bonus deve essere positivo");
        }

        this.bonus = bonus;
    }

    @Override
    public String getName() {
        return "Pozione di forza (+" + this.bonus + " attacco)";
    }

    @Override
    public boolean use(Hero hero) {
        if (!hero.isAlive()) {
            return false;
        }

        hero.increaseAttack(this.bonus);
        return true;
    }

    public int getBonus() {
        return this.bonus;
    }
}