package it.unicam.cs.mpgc.rpg130540.game;

import it.unicam.cs.mpgc.rpg130540.game.enumerators.TurnPhase;
import it.unicam.cs.mpgc.rpg130540.model.heroes.Hero;
import it.unicam.cs.mpgc.rpg130540.model.enemies.Enemy;
import it.unicam.cs.mpgc.rpg130540.abilities.Ability;
import it.unicam.cs.mpgc.rpg130540.model.enemies.EnemyAttack;

/**
 * Classe Fight che gestisce un singolo combattimento con azione dell'eroe e scelta
 * difensiva contro il colpo annunciato. Consente un uso dell'abilità speciale e
 * uno della difesa speciale per incontro.
 */
public class Fight {
    /**
     * Eroe mantenuto nella partita.
     */
    private final Hero hero;
    /**
     * Nemico corrente associato al combattimento o incontro.
     */
    private final Enemy enemy;
    /**
     * Fase interna del combattimento.
     */
    private TurnPhase turnPhase;
    /**
     * Indica se l'abilità speciale è già stata usata in questo combattimento.
     */
    private boolean abilityUsed;
    /**
     * Indica se la difesa speciale è già stata usata in questo combattimento.
     */
    private boolean defenseUsed;
    /**
     * Colpo annunciato e non ancora risolto, nullo quando assente.
     */
    private EnemyAttack pendingAttack;

    /**
     * Costruttore della classe Fight che prepara uno scontro tra l'eroe e il nemico indicati.
     * Prepara il combattimento con l'eroe come primo attore. Se uno dei partecipanti è già
     * sconfitto, imposta subito ENDED.
     *
     * @param hero eroe coinvolto, non nullo
     * @param enemy nemico coinvolto, non nullo
     * @throws NullPointerException se uno dei partecipanti è nullo
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
     * Verifica se almeno un partecipante è sconfitto.
     *
     * @return true se l'eroe o il nemico non è più vivo,
     *         false altrimenti
     */
    public boolean isEnded() {
        return !hero.isAlive() || !enemy.isAlive();
    }

    /**
     * Esegue l'attacco ordinario soltanto durante HERO_ACTION. Applica le protezioni
     * del nemico e, se il combattimento continua, prepara il colpo annunciato passando
     * a HERO_DEFENSE. Non applica immediatamente il contrattacco.
     *
     * Nelle altre fasi non ha effetto.
     */
    public void attack() {
        if (this.turnPhase != TurnPhase.HERO_ACTION)
            return;
        enemy.receiveHit(hero.getAttack());
        finishHeroAction(false);
    }

    /**
     * Usa l'abilità speciale soltanto durante HERO_ACTION e una sola volta per incontro.
     * Calcola il danno, applica le difese nemiche e conclude l'azione secondo l'effetto
     * di impedimento della risposta.
     *
     * Una chiamata non consentita non ha effetto.
     */
    public void useSpecialAbility() {
        if (this.turnPhase != TurnPhase.HERO_ACTION || this.abilityUsed)
            return;
        Ability ability = hero.getSpecialAbility();
        enemy.receiveHit(ability.calculateDamage(hero.getAttack()));
        this.abilityUsed = true;
        finishHeroAction(ability.preventsCounterattack());
    }

    /**
     * Conclude l'azione dell'eroe: termina lo scontro, salta la risposta nemica oppure
     * prepara il colpo e attende la scelta difensiva.
     *
     * @param preventsCounterattack true se la risposta nemica deve essere saltata
     */
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

    /**
     * Applica integralmente il colpo annunciato durante HERO_DEFENSE e conclude la risposta
     * nemica.
     *
     * Nelle altre fasi non ha effetto.
     */
    public void receiveAttack() {
        if (this.turnPhase != TurnPhase.HERO_DEFENSE) {
            return;
        }
        resolveEnemyAttack(this.pendingAttack.getDamage());
    }

    /**
     * Calcola e applica il danno dopo la difesa speciale durante HERO_DEFENSE, consumandone
     * l'unico utilizzo dell'incontro.
     *
     * Nelle altre fasi o dopo il consumo non ha effetto.
     */
    public void useSpecialDefense() {
        if (this.turnPhase != TurnPhase.HERO_DEFENSE || this.defenseUsed) {
            return;
        }
        int damage = hero.getSpecialDefense().calculateReceivedDamage(this.pendingAttack.getDamage());
        this.defenseUsed = true;
        resolveEnemyAttack(damage);
    }

    /**
     * Applica il danno all'eroe, cancella il colpo pendente e passa a ENDED oppure HERO_ACTION.
     *
     * @param damage danno residuo da applicare, non negativo
     * @throws IllegalArgumentException se il danno è negativo
     */
    private void resolveEnemyAttack(int damage) {
        hero.takeDamage(damage);
        this.pendingAttack = null;
        if (isEnded()) {
            this.turnPhase = TurnPhase.ENDED;
        } else {
            this.turnPhase = TurnPhase.HERO_ACTION;
        }
    }

    /**
     * Restituisce la fase interna corrente del combattimento.
     *
     * @return fase interna del combattimento
     */
    public TurnPhase getTurnPhase() {
        return this.turnPhase;
    }

    /**
     * Restituisce il danno del colpo annunciato, senza applicarlo.
     *
     * @return danno annunciato, oppure zero se non vi è un colpo pendente
     */
    public int getPendingDamage() {
        if (this.pendingAttack == null) {
            return 0;
        }
        return this.pendingAttack.getDamage();
    }

    /**
     * Restituisce il nome del colpo in attesa della scelta difensiva.
     *
     * @return nome del colpo annunciato, oppure stringa vuota se assente
     */
    public String getPendingAttackName() {
        if (this.pendingAttack == null) {
            return "";
        }
        return this.pendingAttack.getName();
    }

    /**
     * Verifica se l'abilità speciale è ancora disponibile nella fase corrente.
     *
     * @return true se la fase è HERO_ACTION e l’abilità non è ancora stata usata
     */
    public boolean canUseSpecialAbility() {
        return this.turnPhase == TurnPhase.HERO_ACTION && !this.abilityUsed;
    }

    /**
     * Verifica se la difesa speciale è ancora disponibile nella fase corrente.
     *
     * @return true se la fase è HERO_DEFENSE e la difesa non è ancora stata usata
     */
    public boolean canUseSpecialDefense() {
        return this.turnPhase == TurnPhase.HERO_DEFENSE && !this.defenseUsed;
    }

    /**
     * Tenta di usare un manufatto durante HERO_ACTION. Solo un utilizzo riuscito consuma
     * l'azione e prepara la risposta nemica; nelle altre fasi non modifica il combattimento.
     *
     * @param index indice del manufatto nell'inventario
     * @return true se il manufatto è stato usato,
     *         false se fase, indice o condizioni d'uso non lo consentono
     */
    public boolean useArtifact(int index) {
        if (this.turnPhase != TurnPhase.HERO_ACTION) {
            return false;
        }
        if (!this.hero.getInventory().use(index, this.hero)) {
            return false;
        }
        finishHeroAction(false);
        return true;
    }
}