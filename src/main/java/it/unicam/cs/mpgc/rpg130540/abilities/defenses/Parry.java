package it.unicam.cs.mpgc.rpg130540.abilities.defenses;

/**
 * Classe Parry che implementa l'interfaccia Defense realizza una parata che riduce
 * il danno annunciato di 2 punti, fino a un minimo di zero.
 */
public class Parry implements Defense {

    @Override
    public String getName() {
        return "Parata";
    }

    /**
     * Riduce il danno annunciato di 2 punti, senza scendere sotto zero.
     *
     * @param incomingDamage danno annunciato prima della difesa, non negativo
     * @return danno ricevuto -2, con minimo zero
     * @throws IllegalArgumentException se il danno in ingresso è negativo
     */
    @Override
    public int calculateReceivedDamage(int incomingDamage) {
        if (incomingDamage < 0) {
            throw new IllegalArgumentException("Il danno non può essere negativo");
        }
        return Math.max(0, incomingDamage - 2);
    }
}