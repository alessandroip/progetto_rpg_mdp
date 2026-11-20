package it.unicam.cs.mpgc.rpg130540.game;

import it.unicam.cs.mpgc.rpg130540.items.Artifact;
import it.unicam.cs.mpgc.rpg130540.items.HealingPotion;
import it.unicam.cs.mpgc.rpg130540.items.StrengthPotion;
import it.unicam.cs.mpgc.rpg130540.items.Blowpipe;
import it.unicam.cs.mpgc.rpg130540.items.WildHealingPlant;

import java.util.ArrayList;
import java.util.List;

/**
 * Gestisce onore e bottino da donare e crea le opzioni di ricompensa attualmente disponibili.
 * GameSession controlla l'ordine delle fasi e impedisce assegnazioni ripetute tramite le azioni
 * della partita.
 */
public class Rewards {
    private int honor;
    private int pendingCoins;

    /**
     * Registra una vittoria aggiungendo un punto onore e il bottino indicato. La fase della
     * sessione deve impedire registrazioni ripetute della stessa vittoria.
     *
     * @param coins monete del bottino, maggiori di zero
     * @throws IllegalArgumentException se il bottino non è positivo
     * @throws IllegalStateException se il bottino precedente non è stato donato
     */
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

    /**
     * Azzera le monete pendenti senza modificare l'onore. Il controllo della fase è
     * responsabilità di GameSession.
     */
    public void donateLoot() {
        this.pendingCoins = 0;
    }

    public int getHonor() {
        return this.honor;
    }

    public int getPendingCoins() {
        return this.pendingCoins;
    }

    /**
     * Crea nuove istanze dei quattro manufatti offerti: pozione di forza da due, cerbottana da
     * uno, pozione curativa da dodici e pianta da tre. Non li assegna ancora all'eroe.
     *
     * @return nuova lista delle ricompense selezionabili
     */
    public List<Artifact> createRewardOptions() {
        List<Artifact> options = new ArrayList<>();
        options.add(new StrengthPotion(2));
        options.add(new Blowpipe(1));
        options.add(new HealingPotion(12));
        options.add(new WildHealingPlant(3));
        return options;
    }

    /**
     * Ripristina l'onore di una partita caricata e azzera il bottino pendente.
     *
     * @param savedHonor onore da ripristinare, non negativo
     * @throws IllegalArgumentException se l'onore è negativo
     */
    public void restoreHonor(int savedHonor) {
        if (savedHonor < 0) {
            throw new IllegalArgumentException("L'onore non può essere negativo");
        }
        this.honor = savedHonor;
        this.pendingCoins = 0;
    }
}
