package it.unicam.cs.mpgc.rpg130540.ui;

import it.unicam.cs.mpgc.rpg130540.ui.controllers.MainMenuController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;

/**
 * Carica la schermata FXML del menu e configura il controller.
 *
 * L’ordine è importante: loader.load() crea interfaccia e controller;
 * poi getController() recupera quel controller e configure() gli assegna le azioni.
 */
public class MainMenu {

    public Scene createScene(Runnable newGame, Runnable loadGame) {
        URL resource = getClass().getResource("/fxml/MainMenu.fxml");

        if (resource == null) {
            throw new IllegalStateException("File /fxml/MainMEnu.fxml non trovato");
        }
        FXMLLoader loader = new FXMLLoader(resource);

        try {
            Parent root = loader.load();

            MainMenuController controller = loader.getController();
            controller.configure(newGame, loadGame);

            return new Scene(root, 750, 400);
        } catch (IOException exception) {
            throw new IllegalStateException(
                    "Impossibile caricare il menu principale",
                    exception
            );
        }
    }

}