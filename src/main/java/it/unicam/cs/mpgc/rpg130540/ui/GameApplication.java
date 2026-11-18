package it.unicam.cs.mpgc.rpg130540.ui;

import it.unicam.cs.mpgc.rpg130540.game.EncounterFactory;
import it.unicam.cs.mpgc.rpg130540.game.GameSession;
import it.unicam.cs.mpgc.rpg130540.persistence.GameSaveService;
import it.unicam.cs.mpgc.rpg130540.persistence.SaveRepository;
import it.unicam.cs.mpgc.rpg130540.persistence.SaveDataValidator;
import it.unicam.cs.mpgc.rpg130540.persistence.SaveManager;
import it.unicam.cs.mpgc.rpg130540.persistence.GameSaver;
import it.unicam.cs.mpgc.rpg130540.persistence.SaveLoader;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class GameApplication extends Application {

    private EncounterFactory encounterFactory;
    private GameSaveService saveService;

    @Override
    public void start(Stage stage) {
        this.encounterFactory = new EncounterFactory();

        SaveRepository repository = new SaveManager();

        SaveDataValidator validator =
                new SaveDataValidator(this.encounterFactory);

        GameSaver saver = new GameSaver();

        SaveLoader loader =
                new SaveLoader(this.encounterFactory);

        this.saveService = new GameSaveService(
                repository,
                validator,
                saver,
                loader
        );

        MainMenu menu = new MainMenu();

        stage.setTitle("Robin Hood RPG");
        stage.setScene(menu.createScene(
                () -> showHeroSelection(stage),
                () -> loadGame(stage)
        ));
        stage.show();
    }

    private void showHeroSelection(Stage stage) {
        HeroSelection selection = new HeroSelection();

        stage.setScene(selection.createScene(hero -> {
            GameSession session = new GameSession(
                    hero, this.encounterFactory
            );

            showGame(stage, session);
        }));
    }

    private void loadGame(Stage stage) {
        try {
            GameSession session = this.saveService.load();
            showGame(stage, session);

        } catch (IOException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Caricamento");
            alert.setHeaderText("Impossibile caricare la partita");
            alert.setContentText(exception.getMessage());
            alert.showAndWait();
        }
    }

    private void showGame(Stage stage, GameSession session) {
        GameWindow window = new GameWindow(session, this.saveService);
        stage.setScene(window.createScene());
    }
}