package it.unicam.cs.mpgc.rpg130540.ui.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;

/**
 * Gestisce le azioni del menu principale.
 */
public class MainMenuController {
    private Runnable newGame;
    private Runnable loadGame;

    /**
     * Collega le azioni definite dall'applicazione ai pulsanti del menu.
     */
    public void configure(Runnable newGame, Runnable loadGame) {
        if (newGame == null || loadGame == null) {
            throw new IllegalArgumentException("Le azioni del menu sono obbligatorie");
        }
        this.newGame = newGame;
        this.loadGame = loadGame;
    }

    /**
     * @FXML permette al caricatore FXML di accedere a questi metodi privati.
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
