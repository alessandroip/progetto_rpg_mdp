package it.unicam.cs.mpgc.rpg130540.abilities.defenses;

public interface Defense {
    String getName();
    int calculateReceivedDamage(int incomingDamage);
}
