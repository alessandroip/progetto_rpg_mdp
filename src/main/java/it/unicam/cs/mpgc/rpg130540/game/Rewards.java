package it.unicam.cs.mpgc.rpg130540.game;

public class Rewards {
    private int honor;
    private int pendingCoins;

    public void registerVictory(int coins) {
        if (coins <= 0) {
            throw new IllegalArgumentException("Il bottino deve essere positivo");
        }
        if (this.pendingCoins > 0) {
            throw new IllegalStateException("Il bottino precedente deve essere donato");
        }
        this.honor++;
        this.pendingCoins = coins;
    }

    public void donateLoot() {
        this.pendingCoins = 0;
    }

    public int getHonor() {
        return this.honor;
    }

    public int getPendingCoins() {
        return this.pendingCoins;
    }
}
