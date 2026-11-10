package it.unicam.cs.mpgc.rpg130540;

import it.unicam.cs.mpgc.rpg130540.model.Hero;
import it.unicam.cs.mpgc.rpg130540.model.Enemy;
import it.unicam.cs.mpgc.rpg130540.game.Fight;
import it.unicam.cs.mpgc.rpg130540.game.GameSession;
import it.unicam.cs.mpgc.rpg130540.game.Encounter;

import java.util.List;

/**
 * Punto di avvio di Robin Hood RPG.
 *
 * Configura una simulazione automatica in console con Little John
 * e dueincontri di proga. Affida la gestioone della partita a GameSession
 * e mostra nel terminale i risultati della simulazione.
 */
public class Main {

    /**
     * Crea l'eroe e i due incontri di prova, quindi avvia la sessione.
     *
     * Esegue un turno alla volta finché il combattimento è in corso.
     * Dopo il primo socntro, mostra il bottino prima e dopo la donazione
     * e avvia il secondo scontro, richiede la donazione finale e stampa
     * la fase raggiunta e i punti onore accumulati.
     *
     * @param args argomenti della riga di comando, attualmente inutilizzati
     */
    void main(String[] args) {
        Hero littleJohn = new Hero("Little John", 18, 3);
        Encounter firstEncounter = new Encounter("Guardia semplice", 10, 4, 10);
        Encounter secondEncounter = new Encounter("Guardia forte", 12, 1, 20);
        GameSession session = new GameSession(littleJohn, List.of(firstEncounter, secondEncounter));

        while (session.getPhase() == GameSession.Phase.FIGHTING) {
            session.attack();
        }

        System.out.println("Monete prima: " + session.getPendingCoins());
        session.donateLoot();
        System.out.println("Monete dopo: " + session.getPendingCoins());
        System.out.println("Fase: " + session.getPhase());
        System.out.println("Onore: " + session.getHonor());

        session.startNextEncounter();
        System.out.println("PV all'inizio del secondo incontro: " + littleJohn.getLifePoints());

        while (session.getPhase() == GameSession.Phase.FIGHTING) {
            session.attack();
        }

        System.out.println("Secondo bottino: " + session.getPendingCoins());
        session.donateLoot();
        System.out.println("Fase finale: " + session.getPhase());
        System.out.println("Onore finale: " + session.getHonor());
    }
}