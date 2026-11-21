package it.unicam.cs.mpgc.rpg130540.ui.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;

/**
 * Gestisce le azioni dopo la sconfitta: nuova selezione dell'eroe, ritorno
 * al menu e uscita. Ricominciare non ripristina il medesimo incontro e non
 * cancella il salvataggio.
 */
public class GameOverController {
    /**
     * Azione che riapre la scelta dell'eroe.
     */
    private Runnable restart;
    /**
     * Azione di ritorno al menu.
     */
    private Runnable returnToMenu;

    /**
     * Assegna le azioni di nuova selezione e ritorno al menu.
     *
     * @param restart azione che riapre la scelta dell'eroe, non nulla
     * @param returnToMenu azione che mostra il menu principale, non nulla
     * @throws IllegalArgumentException se almeno una dipendenza o azione richiesta è nulla
     */
    public void configure(Runnable restart, Runnable returnToMenu) {
        if (restart == null || returnToMenu == null) {
            throw new IllegalArgumentException("Le azioni della schermata sono obbligatorie");
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