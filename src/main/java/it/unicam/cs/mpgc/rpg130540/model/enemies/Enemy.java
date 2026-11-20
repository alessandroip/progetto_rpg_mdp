package it.unicam.cs.mpgc.rpg130540.model.enemies;

import it.unicam.cs.mpgc.rpg130540.model.Entity;

/**
 * Classe Enemy (sottoclasse di Entity) che rappresenta le entità antagoniste del gioco e i
 * comportamenti che condividono oltre alle caratteristiche di Entity.
 */
public abstract class Enemy extends Entity {

    /**
     * Il costruttore crea un nemico con i punti vita al massimo.
     *
     * @param name nome del nemico
     * @param lifePointsMax punti vita massimi, maggiori di zero
     * @param attack valore dell'attacco base, maggiore di zero
     * @throws IllegalArgumentException se il nome è nullo o privo di caratteri
     *          visibili oppure se vita massima o attacco sono minori o uguali
     *          a zero
     */
    public Enemy(String name, int lifePointsMax, int attack) {
        super(name, lifePointsMax, attack);
    }

    /**
     * Applica un colpo senza riduzioni difensive. Le sottoclassi possono ridefinire
     * il metodo per applicare protezioni prima di sottrarre i punti vita.
     *
     * @param incomingDamage danno in ingresso prima delle difese, non negativo
     * @throws IllegalArgumentException se il danno è negativo
     */
    public void receiveHit(int incomingDamage) {
        takeDamage(incomingDamage);
    }

    /**
     * Prepara un nuovo attacco base senza applicarlo all'eroe.
     *
     * @return descrizione del colpo con l'attacco corrente del nemico
     */
    public EnemyAttack prepareAttack() {
        return new EnemyAttack("Attacco base", getAttack());
    }
}