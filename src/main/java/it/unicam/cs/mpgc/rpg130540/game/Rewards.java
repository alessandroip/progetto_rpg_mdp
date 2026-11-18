package it.unicam.cs.mpgc.rpg130540.game;

import it.unicam.cs.mpgc.rpg130540.items.Artifact;
import it.unicam.cs.mpgc.rpg130540.items.HealingPotion;
import it.unicam.cs.mpgc.rpg130540.items.StrengthPotion;

import java.util.ArrayList;
import java.util.List;

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

    public List<Artifact> createRewardOptions() {
        List<Artifact> options = new ArrayList<>();
        options.add(new HealingPotion(12));
        options.add(new StrengthPotion(2));
        return options;
    }

    public void restoreHonor(int savedHonor) {
        if (savedHonor < 0) {
            throw new IllegalArgumentException("L'onore non può essere negativo");
        }

        this.honor = savedHonor;
        this.pendingCoins = 0;
    }
}
