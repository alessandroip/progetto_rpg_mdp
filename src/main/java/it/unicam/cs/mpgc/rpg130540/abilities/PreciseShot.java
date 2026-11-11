package it.unicam.cs.mpgc.rpg130540.abilities;

public class PreciseShot implements Ability {

    @Override
    public String getName() {
        return "Tiro di precisione";
    }

    @Override
    public int calculateDamage(int baseAttack) {
        return baseAttack + 3;
    }

    @Override
    public boolean preventsCounterattack() {
        return false;
    }
}
