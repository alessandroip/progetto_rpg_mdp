package it.unicam.cs.mpgc.rpg130540.ui.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;

/**
 * Gestisce le azioni disponibili dopo la vittoria finale.
 */
public class VictoryController {

    private Runnable returnToMenu;

    public void configure(Runnable returnToMenu) {
        if (returnToMenu == null) {
            throw new IllegalArgumentException(
                    "L'azione di ritorno al menu è obbligatoria"
            );
        }

        this.returnToMenu = returnToMenu;
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