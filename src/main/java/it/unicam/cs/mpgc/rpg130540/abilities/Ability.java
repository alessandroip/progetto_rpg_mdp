package it.unicam.cs.mpgc.rpg130540.abilities;

public interface Ability {
    String getName();
    int calculateDamage(int baseAttack);
    boolean preventsCounterattack();
}
