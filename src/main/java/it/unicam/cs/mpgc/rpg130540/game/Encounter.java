package it.unicam.cs.mpgc.rpg130540.game;

import it.unicam.cs.mpgc.rpg130540.model.Enemy;

/**
 * La classe Encounter descrive l'avversario e il bottino previsto per un incontro.
 */
public class Encounter {
    private final String enemyName;
    private final int enemyLifePoints;
    private final int enemyAttack;
    private final int rewardCoins;

    /**
     * Il costruttore della classe Encounter memorizza le caratteristiche iniziali dell'incontro.
     *
     * @param enemyName nome dell'avversario
     * @param enemyLifePoints punti vita iniziali dell'avversario
     * @param enemyAttack attacco base dell'avversario
     * @param rewardCoins monete ottenute in caso di vittoria
     */
    public Encounter(String enemyName, int enemyLifePoints, int enemyAttack, int rewardCoins) {
        this.enemyName = enemyName;
        this.enemyLifePoints = enemyLifePoints;
        this.enemyAttack = enemyAttack;
        this.rewardCoins = rewardCoins;
    }

    /**
     * Il metodo createEnemy() crea un nuovo avversario con le statistiche iniziali dell'incontro.
     *
     * @return il nemico pronto per il combattimento
     */
    public Enemy createEnemy() {
        return new Enemy(enemyName, enemyLifePoints, enemyAttack);
    }


    /**
     * Metodo getter.
     *
     * @return le monete previste come bottino in caso di vittoria.
     */
    public int getRewardCoins() {
        return rewardCoins;
    }
}
