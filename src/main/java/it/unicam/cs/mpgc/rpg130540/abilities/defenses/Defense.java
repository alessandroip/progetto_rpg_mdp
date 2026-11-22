package it.unicam.cs.mpgc.rpg130540.abilities.defenses;

/**
 * Interfaccia Defense che definisce una difesa che calcola il danno residuo
 * di un colpo annunciato.
 *
 * L'applicazione del danno e il limite di utilizzo sono gestiti da Fight.
 */
public interface Defense {

    /**
     * Restituisce il nome della difesa mostrato al giocatore.
     *
     * @return nome della difesa
     */
    String getName();

    /**
     * Calcola il danno residuo senza modificare direttamente i punti vita.
     *
     * @param incomingDamage danno annunciato prima della difesa, non negativo
     * @return danno residuo non negativo da applicare
     */
    int calculateReceivedDamage(int incomingDamage);
}