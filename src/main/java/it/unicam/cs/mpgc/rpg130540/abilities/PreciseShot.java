package it.unicam.cs.mpgc.rpg130540.abilities;

/**
 * Classe PreciseShot che implementa l'interfaccia Ability realizza il tiro di precisione:
 * aggiunge 3 punti all'attacco ricevuto e non impedisce il contrattacco.
 *
 * Il danno resta soggetto alle difese del nemico.
 */
public class PreciseShot implements Ability {

    @Override
    public String getName() {
        return "Tiro di precisione";
    }

    /**
     * Calcola il danno aggiungendo 3 punti all'attacco ricevuto.
     * Non ignora le protezioni del nemico.
     *
     * @param baseAttack attacco corrente dell'eroe, positivo
     * @return attacco ricevuto + 3
     */
    @Override
    public int calculateDamage(int baseAttack) {
        return baseAttack + 3;
    }

    /**
     * Consente al nemico sopravvissuto di preparare la propria risposta
     *
     * @return false
     */
    @Override
    public boolean preventsCounterattack() {
        return false;
    }
}