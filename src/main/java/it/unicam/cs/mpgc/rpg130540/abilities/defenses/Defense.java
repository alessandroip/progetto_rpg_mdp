package it.unicam.cs.mpgc.rpg130540.defenses;

public interface Defense {
    String getName();
    int calculateReceivedDamage(int incomingDamage);
}
