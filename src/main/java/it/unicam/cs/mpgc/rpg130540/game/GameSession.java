package it.unicam.cs.mpgc.rpg130540.game;

import it.unicam.cs.mpgc.rpg130540.model.Hero;
import it.unicam.cs.mpgc.rpg130540.model.Enemy;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe GameSession che gestisce le fasi della partita e l'onore.
 */
public class GameSession {
    private Enemy enemy;
    private Fight fight;
    private Phase phase = Phase.FIGHTING;
    private int honor;
    private int pendingCoins;
    private final Hero hero;
    private final List<Encounter> encounters;
    private int encounterIndex;

    /**
     * Phase indica la fase corrente della sessione di gioco.
     */
    public enum Phase {
        FIGHTING,
        DONATION_PENDING,
        RETRY_PENDING,
        BETWEEN_ENCOUNTERS,
        COMPLETED
    }

    /**
     * Il costruttore della classe GameSession avvia la partita dal primo incontro.
     *
     * Esegue un controllo sul numero degli incontri che non può essere zero.
     *
     * @param hero eroe scelto per tutta la partita
     * @param encounters incontri da affrontare in ordine
     * @throws IllegalArgumentException se la lista è vuota
     */
    public GameSession(Hero hero, List<Encounter> encounters) {
        if (encounters.isEmpty()) {
            throw new IllegalArgumentException("Serve almeno un incontro");
        }
        this.hero = hero;
        this.encounters = new ArrayList<>(encounters);
        startCurrentEncounter();
    }

    /**
     * Metodo startCurrentEncounter() che crea il nemico e il combattimento per l'incontro corrente.
     */
    private void startCurrentEncounter() {
        Encounter encounter = encounters.get(encounterIndex);
        this.enemy = encounter.createEnemy();
        this.fight = new Fight(hero, enemy);
        this.phase = Phase.FIGHTING;
    }

    /**
     * Il metodo attack() esegue un turno se la sessione è in fase FIGHTING.
     *
     * Se l'eroe vince, assegna un punto onore e il bottino dell'incontro, quindi passa a DONATION_PENDIING.
     * Se l'eroe perde, passa a RETRY_PENDING.
     * Se lo scontro prosegue, la fase resta FIGHTING.
     *
     * Nelle altre fasi, la chiamata non ha effetto.
     */
    public void attack() {
        if (phase != Phase.FIGHTING) {
            return;
        }

        fight.attack();
        if (fight.isEnded()) {
            if (enemy.getLifePoints() == 0) {
                honor++;
                pendingCoins = encounters.get(encounterIndex).getRewardCoins();
                phase = Phase.DONATION_PENDING;
            } else {
                phase = Phase.RETRY_PENDING;
            }
        }
    }

    /**
     * Metodo getter che restituisce la fase corrente della sessione.
     *
     * @return la fase che determina quali azioni sono disponibili
     */
    public Phase getPhase() {
        return phase;
    }

    /**
     * Metodo getter che restituisce il totale dei punti onore accumulati nella sessione.
     *
     * @return i punti onore ottenuti vincendo gli incontri
     */
    public int getHonor() {
        return honor;
    }

    /**
     * Il metodo donateLoot() dona il bottino dell'incontro appena vinto.
     *
     * Se restano altri incontri, la sessione passa a BETWEEN_ENCOUNTERS.
     * Dopo la donazione del bottino dell'ultimo incontro, passa a COMPLETED.
     */
    public void donateLoot() {
        if (phase != Phase.DONATION_PENDING) {
            return;
        }
        pendingCoins = 0;
        if (encounterIndex == encounters.size() - 1) {
            phase = Phase.COMPLETED;
        } else {
            phase = Phase.BETWEEN_ENCOUNTERS;
        }
    }

    /**
     * Avvia l'incontro successivo quando la donazione è conclusa e rimangono avversari da affrontare.
     */
    public void startNextEncounter() {
        if (phase != Phase.BETWEEN_ENCOUNTERS) {
            return;
        }
        encounterIndex++;
        startCurrentEncounter();
    }
    /**
     * Metodo getter che determina il bottino non donato.
     *
     * @return le monete del bottino ancora da donare
     */
    public int getPendingCoins() {
        return pendingCoins;
    }
}
