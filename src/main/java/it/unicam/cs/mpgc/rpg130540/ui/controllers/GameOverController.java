package it.unicam.cs.mpgc.rpg130540.ui.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;

/**
 * Gestisce le azioni disponibili dopo una sconfitta.
 */
public class GameOverController {

    private Runnable restart;
    private Runnable returnToMenu;

    public void configure(
            Runnable restart,
            Runnable returnToMenu
    ) {
        if (restart == null || returnToMenu == null) {
            throw new IllegalArgumentException(
                    "Le azioni della schermata sono obbligatorie"
            );
        }

        this.restart = restart;
        this.returnToMenu = returnToMenu;
    }

    @FXML
    private void onRestart() {
        this.restart.run();
    }

    @FXML
    private void onMenu() {
        this.returnToMenu.run();
    }

    @FXML
    private void onExit() {
        Platform.exit();
    }
}