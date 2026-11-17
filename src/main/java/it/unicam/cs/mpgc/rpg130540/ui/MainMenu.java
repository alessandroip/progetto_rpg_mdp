package it.unicam.cs.mpgc.rpg130540.ui;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Questa classe mostra il menu principale del gioco.
 * Si possono fare tre scelte:
 * - avviare una nuova partita
 * - caricare una partita precedente
 * - uscire dal gioco
 */
public class MainMenu {

    public Scene createScene(Runnable newGame, Runnable loadGame) {
        Label title = new Label("Robin Hood RPG");
        title.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-font-style: italic;");

        Button newGameButton = new Button("Nuova partita");
        Button loadGameButton = new Button("Carica partita");
        Button exitButton = new Button("Esci");

        newGameButton.setOnAction(event -> newGame.run());
        loadGameButton.setOnAction(event -> loadGame.run());
        exitButton.setOnAction(event -> Platform.exit());

        VBox root = new VBox(
                20, title, newGameButton, loadGameButton, exitButton
        );
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);

        return new Scene(root, 750, 400);
    }

}