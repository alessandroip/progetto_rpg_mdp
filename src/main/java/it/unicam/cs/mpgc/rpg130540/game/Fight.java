package it.unicam.cs.mpgc.rpg130540.game;

import it.unicam.cs.mpgc.rpg130540.game.enumerators.TurnPhase;
import it.unicam.cs.mpgc.rpg130540.model.heroes.Hero;
import it.unicam.cs.mpgc.rpg130540.model.enemies.Enemy;
import it.unicam.cs.mpgc.rpg130540.abilities.Ability;
import it.unicam.cs.mpgc.rpg130540.model.enemies.EnemyAttack;

/**
 * Classe Fight che gestisce un singolo combattimento.
 */
public class Fight {
    private final Hero hero;
    private final Enemy enemy;
    private TurnPhase turnPhase;
    private boolean abilityUsed;
    private boolean defenseUsed;
    private EnemyAttack pendingAttack;

    /**
     * Costruttore della classe Fight che prepara uno scontro tra l'eroe e il nemico indicati.
     *
     * @param hero eroe che agisce per primo
     * @param enemy nemico che può contrattaccare
     */
    public Fight(Hero hero, Enemy enemy) {
        this.hero = hero;
        this.enemy = enemy;

        if (isEnded()) {
            this.turnPhase = TurnPhase.ENDED;
        } else {
            this.turnPhase = TurnPhase.HERO_ACTION;
        }
    }

    /**
     *
     * @return {@code true} se almeno uno dei partecipanti ha zero punti vita,
     *         {@code false} altrimenti
     */
    public boolean isEnded() {
        return !hero.isAlive() || !enemy.isAlive();
    }

    /**
     * Metodo che esegue un turno in cui l'eroe attacca e il nemico contrattacca
     * se sopravvive. Se lo scontro è già terminato, il metodo termina subito.
     */
    public void attack() {
        if (this.turnPhase != TurnPhase.HERO_ACTION)
            return;
        enemy.receiveHit(hero.getAttack());
        finishHeroAction(false);
    }

    public void useSpecialAbility() {
        if (this.turnPhase != TurnPhase.HERO_ACTION || this.abilityUsed)
            return;
        Ability ability = hero.getSpecialAbility();
        enemy.receiveHit(ability.calculateDamage(hero.getAttack()));
        this.abilityUsed = true;
        finishHeroAction(ability.preventsCounterattack());
    }

    private void finishHeroAction(boolean preventsCounterattack) {
        if (isEnded()) {
            this.turnPhase = TurnPhase.ENDED;
        } else if (preventsCounterattack) {
            this.turnPhase = TurnPhase.HERO_ACTION;
        } else {
            this.pendingAttack = enemy.prepareAttack();
            this.turnPhase = TurnPhase.HERO_DEFENSE;
        }
    }

    public void receiveAttack() {
        if (this.turnPhase != TurnPhase.HERO_DEFENSE) {
            return;
        }
        resolveEnemyAttack(this.pendingAttack.getDamage());
    }

    public void useSpecialDefense() {
        if (this.turnPhase != TurnPhase.HERO_DEFENSE || this.defenseUsed) {
            return;
        }
        int damage = hero.getSpecialDefense().calculateReceivedDamage(this.pendingAttack.getDamage());
        this.defenseUsed = true;
        resolveEnemyAttack(damage);
    }

    private void resolveEnemyAttack(int damage) {
        hero.takeDamage(damage);
        this.pendingAttack = null;
        if (isEnded()) {
            this.turnPhase = TurnPhase.ENDED;
        } else {
            this.turnPhase = TurnPhase.HERO_ACTION;
        }
    }

    public TurnPhase getTurnPhase() {
        return this.turnPhase;
    }

    public int getPendingDamage() {
        if (this.pendingAttack == null) {
            return 0;
        }
        return this.pendingAttack.getDamage();
    }

    public String getPendingAttackName() {
        if (this.pendingAttack == null) {
            return "";
        }
        return this.pendingAttack.getName();
    }

    public boolean canUseSpecialAbility() {
        return this.turnPhase == TurnPhase.HERO_ACTION && !this.abilityUsed;
    }

    public boolean canUseSpecialDefense() {
        return this.turnPhase == TurnPhase.HERO_DEFENSE && !this.defenseUsed;
    }
}
