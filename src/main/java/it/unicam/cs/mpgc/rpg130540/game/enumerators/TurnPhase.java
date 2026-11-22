package it.unicam.cs.mpgc.rpg130540.game.enumerators;

/**
 * L'enumeratore TurnPhase indica se il combattimento attende un'azione dell'eroe, una scelta difensiva o è terminato.
 */
public enum TurnPhase {
    HERO_ACTION,
    HERO_DEFENSE,
    ENDED
}