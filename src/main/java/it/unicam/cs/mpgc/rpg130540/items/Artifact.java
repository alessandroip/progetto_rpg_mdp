package it.unicam.cs.mpgc.rpg130540.items;

import it.unicam.cs.mpgc.rpg130540.model.heroes.Hero;

/**
 * Interfaccia Artifact che definisce un manufatto utilizzabile sull'eroe.
 *
 * L'inventario lo rimuove soltanto se l'utilizzo restituisce true e l'eventuale
 * consumo del turno è deciso dal combattimento.
 */
public interface Artifact {

    /**
     * Restituisce il nome descrittivo del manufatto.
     *
     * @return nome mostrato al giocatore
     */
    String getName();

    /**
     * Tenta di applicare l'effetto all'eroe senza rimuovere direttamente il
     * manufatto dall'inventario.
     *
     * Un utilizzo non riuscito non deve applicare l'effetto.
     *
     * @param hero eroe destinatario dell'effetto, non nullo
     * @return true se l'effetto è stato applicato,
     *         false se il manufatto non è utilizzabile nello stato corrente
     */
    boolean use(Hero hero);
}