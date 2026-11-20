package it.unicam.cs.mpgc.rpg130540.model.enemies;

/**
 * Classe Mage che configura il quarto nemico che ha una barriera iniziale da difesa e magie
 * alternate per attaccare.
 */
public class Mage extends Enemy {
    /**
     * Indica se la prossima magia deve essere quella potente.
     */
    private boolean powerfulSpellNext;
    /**
     * Indica se la barriera iniziale è stata consumata.
     */
    private boolean barrierUsed;

    /**
     * Il costruttore crea il nemico mago con 14 punti vita e attacco base 6.
     */
    public Mage() {
        super("Mago", 14, 6);
    }

    /**
     * Crea un attacco alternato: Scintilla arcana pari a metà dell'attacco base con
     * divisione intera, quindi conferisce 3 punti di danno e Esplosione arcana pari
     * all'attacco + 3, quindi 9 punti di danno. Inizia dalla scintilla e avanza
     * l'alternanza a ogni chiamata. Viene utilizzata come in
     * precedenza la modalità di invertire il valore booleano da false a true.
     * Non applica ancora il danno.
     *
     * @return nuova descrizione del prossimo colpo annunciato
     */
    @Override
    public EnemyAttack prepareAttack() {
        EnemyAttack preparedAttack;
        if (this.powerfulSpellNext) {
            preparedAttack = new EnemyAttack(
                    "Esplosione arcana", getAttack() + 3
            );
        } else {
            preparedAttack = new EnemyAttack(
                    "Scintilla arcana", getAttack() / 2
            );
        }
        this.powerfulSpellNext = !this.powerfulSpellNext;
        return preparedAttack;
    }

    /**
     * Annulla il primo colpo positivo consumando la barriera, poi applica normalmente
     * i successivi. Un colpo zero non consuma la barriera.
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
        if (!this.barrierUsed) {
            this.barrierUsed = true;
            return;
        }
        takeDamage(incomingDamage);
    }
}