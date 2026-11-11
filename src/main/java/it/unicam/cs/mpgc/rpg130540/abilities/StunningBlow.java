package it.unicam.cs.mpgc.rpg130540.abilities;

public class StunningBlow implements Ability {

    @Override
    public String getName() {
        return "Colpo stordente";
    }

    @Override
    public int calculateDamage(int baseAttack) {
        return baseAttack;
    }

    @Override
    public boolean preventsCounterattack() {
        return true;
    }
}
