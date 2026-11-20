package it.unicam.cs.mpgc.rpg130540.abilities.defenses;

/**
 * Classe Dodge che implementa l'interfaccia Defense realizza una schivata che annulla
 * completamente il danno del colpo annunciato. Non usa probabilità di successo.
 */
public class Dodge implements Defense {

    @Override
    public String getName() {
        return "Schivata";
    }

    /**
     * Annulla il danno del colpo annunciato.
     *
     * @param incomingDamage danno annunciato prima della difesa, non negativo
     * @return 0
     * @throws IllegalArgumentException se il danno in ingresso è negativo
     */
    @Override
    public int calculateReceivedDamage(int incomingDamage) {
        if (incomingDamage < 0) {
            throw new IllegalArgumentException("Il danno non può essere negativo");
        }
        return 0;
    }
}