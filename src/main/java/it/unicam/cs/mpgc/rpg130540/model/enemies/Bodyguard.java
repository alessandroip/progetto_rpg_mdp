package it.unicam.cs.mpgc.rpg130540.model.enemies;

/**
 * Classe Bodyguard che configura il terzo nemico da affrontare con attacchi alternati
 * e protezione ogni secondo colpo ricevuto.
 */
public class Bodyguard extends Enemy {
    /**
     * Indica se la prossima preparazione deve produrre il colpo pesante.
     */
    private boolean heavyAttackNext;
    /**
     * Numero di colpi positivi ricevuti
     */
    private int receivedHits;

    /**
     * Il costruttore crea il nemico guardia del corpo con 16 punti vita e attacco base 5.
     */
    public Bodyguard() {
        super("Guardia del corpo", 16, 5);
    }

    /**
     * Crea un attacco alternato: Colpo di mazza che da 5 punti di danno e Colpo
     * di mazza pesante che, invertendo il booleano come in StrongGuard, invece ne conferisce 7.
     * La prima chiamata prepara il colpo normale, poi ogni chiamata avanza l'alternanza.
     * Non applica ancora il danno.
     *
     * @return nuova descrizione del prossimo colpo annunciato
     */
    @Override
    public EnemyAttack prepareAttack() {
        EnemyAttack preparedAttack;
        if (this.heavyAttackNext) {
            preparedAttack = new EnemyAttack(
                    "Colpo di mazza pesante", getAttack() + 2
            );
        } else {
            preparedAttack = new EnemyAttack(
                    "Colpo di mazza", getAttack()
            );
        }
        this.heavyAttackNext = !this.heavyAttackNext;
        return preparedAttack;
    }

    /**
     * Riduce di 2 il secondo, il quarto e il sesto colpo, fino ad arrivare a un minimo
     * di zero. Per attuare ciò incrementa la variabile receivedHits. Applica il danno
     * risultante.
     * Rifiuta danni negativi e ignora i colpi da zero senza contarli.
     *
     * @param incomingDamage applica tutto il danno tramite takeDamage(), prima delle protezioni,
     *                       non negativo
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
        this.receivedHits++;
        int effectiveDamage = incomingDamage;
        if (this.receivedHits % 2 == 0) {
            effectiveDamage = Math.max(0, incomingDamage - 2);
        }
        takeDamage(effectiveDamage);
    }
}