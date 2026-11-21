package it.unicam.cs.mpgc.rpg130540.ui.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;

/**
 * Gestisce ritorno al menu e uscita dopo la vittoria finale, raggiunta con
 * la donazione del bottino dell'ultimo incontro.
 */
public class VictoryController {
    /**
     * Azione di ritorno al menu.
     */
    private Runnable returnToMenu;

    /**
     * Assegna il ritorno al menu.
     *
     * @param returnToMenu azione che mostra il menu principale, non nulla
     * @throws IllegalArgumentException se almeno una dipendenza o azione richiesta è nulla
     */
    public void configure(Runnable returnToMenu) {
        if (returnToMenu == null) {
            throw new IllegalArgumentException("L'azione di ritorno al menu è obbligatoria");
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