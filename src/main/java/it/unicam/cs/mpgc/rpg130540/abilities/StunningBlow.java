package it.unicam.cs.mpgc.rpg130540.abilities;

/**
 * Classe StunningBlow che implementa l'interfaccia Ability realizza il colpo stordente:
 * usa l'attacco ricevuto e impedisce la risposta nemica.
 *
 * L'impedimento non dipende dal danno effettivamente subito dal nemico.
 */
public class StunningBlow implements Ability {

    @Override
    public String getName() {
        return "Colpo stordente";
    }

    /**
     * Calcola il danno usando il valore di attacco ricevuto senza bonus numerici.
     *
     * @param baseAttack attacco corrente dell'eroe, positivo
     * @return attacco ricevuto
     */
    @Override
    public int calculateDamage(int baseAttack) {
        return baseAttack;
    }

    /**
     * Impedisce la risposta nemica anche se le protezioni hanno annullato
     * il danno.
     *
     * @return true
     */
    @Override
    public boolean preventsCounterattack() {
        return true;
    }
}