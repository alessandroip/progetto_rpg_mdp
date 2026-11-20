package it.unicam.cs.mpgc.rpg130540.game.enumerators;

/**
 * Indica la fase corrente della partita in cui ci si trova e determina le azioni consentite.
 * È distinta da TurnPhase, che descrive la fase interna del combattimento.
 */
public enum Phase {
    FIGHTING,
    DONATION_PENDING,
    REWARD_PENDING,
    RETRY_PENDING,
    BETWEEN_ENCOUNTERS,
    COMPLETED
}