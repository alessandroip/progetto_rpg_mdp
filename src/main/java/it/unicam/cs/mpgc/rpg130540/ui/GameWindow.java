package it.unicam.cs.mpgc.rpg130540.ui;

import it.unicam.cs.mpgc.rpg130540.game.GameSession;
import it.unicam.cs.mpgc.rpg130540.persistence.GameSaveService;
import it.unicam.cs.mpgc.rpg130540.ui.controllers.GameController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;

/**
 * Carica la schermata FXML della partita
 * e configura il relativo controller.
 */
public class GameWindow {

    private final GameSession session;
    private final GameSaveService saveService;
    private final Runnable onGameOver;
    private final Runnable onVictory;

    public GameWindow(
            GameSession session,
            GameSaveService saveService,
            Runnable onGameOver,
            Runnable onVictory
    ) {
        if (session == null || saveService == null
                || onGameOver == null || onVictory == null) {
            throw new IllegalArgumentException(
                    "I componenti della schermata sono obbligatori"
            );
        }

        this.session = session;
        this.saveService = saveService;
        this.onGameOver = onGameOver;
        this.onVictory = onVictory;
    }

    public Scene createScene() {
        URL resource = getClass().getResource(
                "/fxml/Game.fxml"
        );

        if (resource == null) {
            throw new IllegalStateException(
                    "File /fxml/Game.fxml non trovato"
            );
        }

        FXMLLoader loader = new FXMLLoader(resource);

        try {
            Parent root = loader.load();

            GameController controller = loader.getController();

            controller.configure(
                    this.session,
                    this.saveService,
                    this.onGameOver,
                    this.onVictory
            );

            return new Scene(root, 1200, 720);

        } catch (IOException exception) {
            throw new IllegalStateException(
                    "Impossibile caricare la schermata di gioco",
                    exception
            );
        }
    }
}