package it.unicam.cs.mpgc.rpg130540.abilities;

/**
 * Interfaccia Ability che definisce un'abilità offensiva dell'eroe:
 * nome, calcolo del danno e possibilità di impedire la risposta nemica.
 *
 * Il limite di utilizzo per incontro è gestito da Fight, non dall'abilità.
 */
public interface Ability {

    /**
     * Restituisce il nome dell'abilità mostrato al giocatore.
     *
     * @return nome dell'abilità
     */
    String getName();

    /**
     * Calcola il danno offensivo senza applicarlo al nemico. Riceve l'attacco
     * corrente dell'eroe e le difese nemiche vengono applicate successivamente.
     *
     * @param currentAttack attacco corrente dell'eroe, positivo
     * @return danno da sottoporre alle difese nemiche
     */
    int calculateDamage(int currentAttack);

    /**
     * Indica se l'uso dell'abilità da parte dell'eroe fa saltare la risposta nemica.
     *
     * @return true se il nemico deve saltare la risposta,
     *         false altrimenti
     */
    boolean preventsCounterattack();
}