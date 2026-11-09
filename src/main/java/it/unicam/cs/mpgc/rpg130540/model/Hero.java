package it.unicam.cs.mpgc.rpg130540.model;

/**
 * Classe Hero (sottoclasse di Entity) che rappresenta le entità protagoniste del gioco.
 */
public class Hero extends Entity {

    /**
     * Il costruttore della classe Hero che riprende alcuni attributi della classe Entity e ne aggiunge di nuovi.
     *
     * @param name
     * @param lifePointsMax
     * @param attack
     */
    public Hero(String name, int lifePointsMax, int attack) {
        super(name, lifePointsMax, attack);
    }

    public int getAttack() {
        return this.attack;
    }
}
