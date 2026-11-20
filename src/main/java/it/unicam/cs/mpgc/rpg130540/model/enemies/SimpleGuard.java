package it.unicam.cs.mpgc.rpg130540.model.enemies;

/**
 * Classe che configura il primo nemico da affrontare che esegue solo un attacco base e non ha
 * nessun tipo di riduzione del danno ricevuto.
 */
public class SimpleGuard extends Enemy {

    /**
     * Il costruttore crea il nemico guardia semplice con 10 punti vita e attacco base 4.
     * La ricezione di colpi è quella ereditata da Enemy, quindi senza riduzioni.
     */
    public SimpleGuard() {
        super("Guardia semplice", 10, 4);
    }

    /**
     * Prepara il Fendente della guardia con il valore di attacco corrente.
     * Non applica ancora il danno
     *
     * @return nuova descrizione del prossimo colpo annunciato
     */
    @Override
    public EnemyAttack prepareAttack() {
        return new EnemyAttack("Fendente della guardia", getAttack());
    }
}