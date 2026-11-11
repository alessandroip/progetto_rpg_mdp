package it.unicam.cs.mpgc.rpg130540.model;

/**
 * Classe Hero (sottoclasse di Entity) che rappresenta le entità protagoniste del gioco.
 */
public abstract class Hero extends Entity {

    /**
     * Il costruttore della classe Hero crea un eroe con i punti vita al massimo.
     *
     * @param name nome dell'eroe
     * @param lifePointsMax punti vita massimi, maggiori di zero
     * @param attack valore dell'attacco base, maggiore di zero
     * @throws IllegalArgumentException se il nome è nullo o privo di caratteri
     *          visibili, oppure se vita massima o attacco sono minori o uguali
     *          a zero
     */
    public Hero(String name, int lifePointsMax, int attack) {
        super(name, lifePointsMax, attack);
    }
}
