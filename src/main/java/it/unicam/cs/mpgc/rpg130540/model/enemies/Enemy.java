package it.unicam.cs.mpgc.rpg130540.model;

/**
 * Classe Enemy (sottoclasse di Entity) che rappresenta le entità antagoniste del gioco.
 */
public class Enemy extends Entity {

    /**
     * Il costruttore della classe Enemy crea un nemico con i punti vita al massimo.
     *
     * @param name nome del nemico
     * @param lifePointsMax punti vita massimi, maggiori di zero
     * @param attack valore dell'attacco base, maggiore di zero
     * @throws IllegalArgumentException se il nome è nullo o privo di caratteri
     *          visibili, oppure se vita massima o attacco sono minori o uguali
     *          a zero
     */
    public Enemy(String name, int lifePointsMax, int attack) {
        super(name, lifePointsMax, attack);
    }
}
