package it.unicam.cs.mpgc.rpg130540.model.enemies;

/**
 * Classe del secondo nemico da affrontare che esegue attacchi alternati e ha
 * un'armatura leggera per difendersi.
 */
public class StrongGuard extends Enemy {
    /**
     * Parte da false quindi il primo colpo preparato è quello di attacco base.
     */
    private boolean heavyAttackNext;

    /**
     * Il costruttore crea il nemico guardia forte con 12 punti vita e attacco base 4.
     */
    public StrongGuard() {
        super("Guardia forte", 12, 4);
    }

    /**
     * Crea un attacco alternato: Colpo di spada che da 4 punti di danno e Affondo
     * pesante che, invertendo il booleano da false a true, invece ne conferisce 6.
     * Ogni chiamata avanza l'alternanza. Non applica ancora il danno.
     *
     * @return nuova descrizione del prossimo colpo annunciato
     */
    @Override
    public EnemyAttack prepareAttack() {
        EnemyAttack preparedAttack;

        if (this.heavyAttackNext) {
            preparedAttack = new EnemyAttack("Affondo pesante", getAttack() + 2);
        } else {
            preparedAttack = new EnemyAttack("Colpo di spada", getAttack());
        }
        this.heavyAttackNext = !this.heavyAttackNext;
        return preparedAttack;
    }

    /**
     * Riduce ogni colpo di 1 punto e applica il risultato, mai inferiore a zero.
     *
     * @param incomingDamage danno del colpo prima delle protezioni, non negativo
     * @throws IllegalArgumentException se il danno è negativo
     */
    @Override
    public void receiveHit(int incomingDamage) {
        if (incomingDamage < 0) {
            throw new IllegalArgumentException("Il danno non può essere negativo");
        }
        int effectiveDamage = Math.max(0, incomingDamage - 1);
        takeDamage(effectiveDamage);
    }
}