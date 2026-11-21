package it.unicam.cs.mpgc.rpg130540.ui.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;

/**
 * Gestisce i pulsanti del menu principale eseguendo le azioni configurate
 * dall'applicazione. Non implementa le regole di creazione o caricamento della partita.
 */
public class MainMenuController {
    /**
     * Azione di avvio di una nuova partita.
     */
    private Runnable newGame;
    /**
     * Azione di caricamento dal menu.
     */
    private Runnable loadGame;

    /**
     * Assegna le azioni del menu dopo il caricamento FXML.
     *
     * @param newGame azione che avvia una nuova partita, non nulla
     * @param loadGame azione che carica una partita, non nulla
     * @throws IllegalArgumentException se almeno una dipendenza o azione richiesta è nulla
     */
    public void configure(Runnable newGame, Runnable loadGame) {
        if (newGame == null || loadGame == null) {
            throw new IllegalArgumentException("Le azioni del menu sono obbligatorie");
        }
        this.newGame = newGame;
        this.loadGame = loadGame;
    }

    /**
     * Esegue l'azione di avvio di una nuova partita.
     */
    @FXML
    public void onNewGame() {
        this.newGame.run();
    }

    @FXML
    private void onLoadGame() {
        this.loadGame.run();
    }

    @FXML
    private void onExit() {
        Platform.exit();
    }
}