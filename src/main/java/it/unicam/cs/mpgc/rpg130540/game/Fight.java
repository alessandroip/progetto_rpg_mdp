package it.unicam.cs.mpgc.rpg130540.game;

import it.unicam.cs.mpgc.rpg130540.model.Hero;
import it.unicam.cs.mpgc.rpg130540.model.Enemy;

/**
 * Classe Fight che implementa i combattimenti tra eroi e nemici.
 */
public class Fight {
    private Hero hero;
    private Enemy enemy;

    /**
     * Costruttore della classe Fight che prepara uno scontro tra l'eroe e il nemico indicati.
     *
     * @param hero eroe che agisce per primo
     * @param enemy nemico che può contrattaccare
     */
    public Fight(Hero hero, Enemy enemy) {
        this.hero = hero;
        this.enemy = enemy;
    }

    /**
     *
     * @return {@code true} se almeno uno dei partecipanti ha zero punti vita,
     *         {@code false} altrimenti
     */
    public boolean isEnded() {
        return hero.getLifePoints() == 0 || enemy.getLifePoints() == 0;
    }

    /**
     * Metodo che esegue un turno in cui l'eroe attacca e il nemico contrattacca
     * se sopravvive. Se lo scontro è già terminato, il metodo termina subito.
     */
    public void attack() {
        if (isEnded())
            return;
        enemy.takeDamage(hero.getAttack());
        if (enemy.getLifePoints() > 0) {
            hero.takeDamage(enemy.getAttack());
        }
    }
}
