package it.unicam.cs.mpgc.rpg130540.defenses;

public class Parry implements Defense {

    @Override
    public String getName() {
        return "Parata";
    }

    @Override
    public int calculateReceivedDamage(int incomingDamage) {
        if (incomingDamage < 0) {
            throw new IllegalArgumentException("Il danno non può essere negativo");
        }
        return Math.max(0, incomingDamage - 2);
    }
}
