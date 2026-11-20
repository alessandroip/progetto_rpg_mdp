package it.unicam.cs.mpgc.rpg130540.game;

import it.unicam.cs.mpgc.rpg130540.model.enemies.Enemy;

/**
 * Classe Encounter che associa un nemico concreto al bottino assegnato per la vittoria
 * di un incontro. Il riferimento al nemico è finale, ma lo stato del nemico cambia
 * durante il combattimento.
 */
public class Encounter {

    /**
     * Nemico corrente associato al combattimento o incontro.
     */
    private final Enemy enemy;

    /**
     * Monete assegnabili alla vittoria.
     */
    private final int rewardCoins;

    /**
     * Il costruttore associa un nemico e un bottino a un incontro.
     *
     * @param enemy nemico coinvolto, non nullo
     * @param rewardCoins monete assegnate alla vittoria, maggiori di zero
     * @throws IllegalArgumentException se il nemico è nullo o il bottino non è positivo
     */
    public Encounter(Enemy enemy, int rewardCoins) {
        if (enemy == null) {
            throw new IllegalArgumentException("Il nemico è obbligatorio");
        }
        if (rewardCoins <= 0) {
            throw new IllegalArgumentException("Il bottino deve essere positivo");
        }
        this.enemy = enemy;
        this.rewardCoins = rewardCoins;
    }

    /**
     * Restituisce il nemico effettivo dell'incontro, non una copia.
     *
     * @return nemico associato
     */
    public Enemy getEnemy() {
        return this.enemy;
    }

    /**
     * Restituisce il bottino previsto in caso di vittoria.
     *
     * @return monete da assegnare
     */
    public int getRewardCoins() {
        return this.rewardCoins;
    }
}