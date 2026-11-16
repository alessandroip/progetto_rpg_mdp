package it.unicam.cs.mpgc.rpg130540.ui;

import it.unicam.cs.mpgc.rpg130540.game.EncounterFactory;
import it.unicam.cs.mpgc.rpg130540.game.GameSession;

import javafx.application.Application;
import javafx.stage.Stage;

public class GameApplication extends Application {

    @Override
    public void start(Stage stage) {
        HeroSelection selection = new HeroSelection();

        stage.setTitle("Robin Hood RPG");

        stage.setScene(selection.createScene(hero -> {
            EncounterFactory factory = new EncounterFactory();
            GameSession session = new GameSession(hero, factory);
            GameWindow gameWindow = new GameWindow(session);

            stage.setScene(gameWindow.createScene());
        }));

        stage.show();
    }
}