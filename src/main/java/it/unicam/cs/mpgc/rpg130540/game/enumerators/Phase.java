package it.unicam.cs.mpgc.rpg130540.game.enumerators;

/**
 * Indica la fase corrente della partita.
 */
public enum Phase {
    FIGHTING,
    DONATION_PENDING,
    REWARD_PENDING,
    RETRY_PENDING,
    BETWEEN_ENCOUNTERS,
    COMPLETED
}
