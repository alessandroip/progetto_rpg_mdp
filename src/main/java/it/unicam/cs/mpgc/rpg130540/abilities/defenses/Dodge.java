package it.unicam.cs.mpgc.rpg130540.abilities.defenses;

public class Dodge implements Defense {

    @Override
    public String getName() {
        return "Schivata";
    }

    @Override
    public int calculateReceivedDamage(int incomingDamage) {
        if (incomingDamage < 0) {
            throw new IllegalArgumentException("Il danno non può essere negativo");
        }
        return 0;
    }
}
