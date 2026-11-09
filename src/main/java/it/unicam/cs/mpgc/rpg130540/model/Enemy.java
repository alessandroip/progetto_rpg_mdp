package it.unicam.cs.mpgc.rpg130540.model;

/**
 * Classe Enemy (sottoclasse di Entity) che rappresenta le entità antagoniste del gioco.
 */
public class Enemy extends Entity {

    /**
     * Il costruttore della classe Enemy che riprende alcuni attributi della classe Entity e ne aggiunge di nuovi.
     *
     * @param name
     * @param lifePointsMax
     * @param attack
     */
    public Enemy(String name, int lifePointsMax, int attack) {
        super(name, lifePointsMax, attack);
    }
}
