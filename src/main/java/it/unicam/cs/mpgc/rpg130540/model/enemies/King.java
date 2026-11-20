package it.unicam.cs.mpgc.rpg130540.model.enemies;

/**
 * Classe King che configura il quinto e ultimo nemico da affrontare.
 */
public class King extends Enemy {
    /**
     * Numero di attacchi preparati
     */
    private int preparedAttacks;
    /**
     * Protezioni ancora disponibili sui colpi positivi.
     */
    private int shieldUses = 2;

    /**
     * Il costruttore crea il nemico re con 20 punti vita e attacco base 7.
     */
    public King() {
        super("Re", 20, 7);
    }

    /**
     * Ogni terzo colpo, chiamato Furia del re, viene potenziato di 3 punti, quindi
     * comporta un totale di 10 punti danno. Gli altri colpi, chiamati Colpo della
     * corona, hanno il valore di attacco corrente. Incrementa il numero di attacchi preparati.
     * Non applica ancora il danno.
     *
     * @return nuova descrizione del prossimo colpo annunciato
     */
    @Override
    public EnemyAttack prepareAttack() {
        this.preparedAttacks++;
        if (this.preparedAttacks % 3 == 0) {
            return new EnemyAttack("Furia del re", getAttack() + 3);
        }
        return new EnemyAttack("Colpo della corona", getAttack());
    }

    /**
     * Riduce di 3 punti i primi due colpi positivi, consumando una protezione per
     * colpo, poi applica normalmente i successivi.
     * Un colpo zero non consuma protezioni.
     *
     * @param incomingDamage danno del colpo prima delle protezioni, non negativo
     * @throws IllegalArgumentException se il danno è negativo
     */
    @Override
    public void receiveHit(int incomingDamage) {
        if (incomingDamage < 0) {
            throw new IllegalArgumentException("Il danno non può essere negativo");
        }
        if (incomingDamage == 0) {
            return;
        }
        int effectiveDamage = incomingDamage;
        if (this.shieldUses > 0) {
            effectiveDamage = Math.max(0, incomingDamage - 3);
            this.shieldUses--;
        }
        takeDamage(effectiveDamage);
    }
}