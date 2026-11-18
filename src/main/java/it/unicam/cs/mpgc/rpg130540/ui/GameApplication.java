package it.unicam.cs.mpgc.rpg130540.ui;

import it.unicam.cs.mpgc.rpg130540.game.EncounterFactory;
import it.unicam.cs.mpgc.rpg130540.persistence.GameSaveService;
import it.unicam.cs.mpgc.rpg130540.persistence.SaveRepository;
import it.unicam.cs.mpgc.rpg130540.persistence.SaveDataValidator;
import it.unicam.cs.mpgc.rpg130540.persistence.SaveManager;
import it.unicam.cs.mpgc.rpg130540.persistence.GameSaver;
import it.unicam.cs.mpgc.rpg130540.persistence.SaveLoader;

import javafx.application.Application;
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

        SceneManager sceneManager = new SceneManager(
                stage,
                this.encounterFactory,
                this.saveService
        );

        stage.setTitle("Robin Hood RPG");
        sceneManager.showMenu();
        stage.show();
    }
}