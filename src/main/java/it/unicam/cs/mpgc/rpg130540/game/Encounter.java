package it.unicam.cs.mpgc.rpg130540.game;

import it.unicam.cs.mpgc.rpg130540.model.enemies.Enemy;

public class Encounter {

    private final Enemy enemy;
    private final int rewardCoins;

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

    public Enemy getEnemy() {
        return this.enemy;
    }

    public int getRewardCoins() {
        return this.rewardCoins;
    }
}
