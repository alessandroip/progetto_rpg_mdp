package it.unicam.cs.mpgc.rpg130540;

import it.unicam.cs.mpgc.rpg130540.ui.GameApplication;
import javafx.application.Application;

/** Punto di avvio dell'applicazione JavaFX. */
public class Main {

    /**
     * Avvia l'interfaccia grafica del gioco.
     *
     * @param args argomenti ricevuti dalla riga di comando
     */
    public static void main(String[] args) {
        Application.launch(GameApplication.class, args);
    }
}