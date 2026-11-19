package it.unicam.cs.mpgc.rpg130540;

import it.unicam.cs.mpgc.rpg130540.game.EncounterFactory;
import it.unicam.cs.mpgc.rpg130540.persistence.*;
import it.unicam.cs.mpgc.rpg130540.ui.SceneManager;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Avvia l'applicazione JavaFX e configura i componenti necessari al gioco e alla persistenza.
 */
public class Main extends Application {

    /**
     * Inizializza l'applicazione e mostra il menu principale.
     *
     * Crea la factory degli incontri, i componenti della persistenza e il gestore delle
     * schermate, collegandoli tra loro.
     * Imposta il titolo della finestra principale e la rende visibile.
     *
     * Questo metodo viene chiamato da JavaFX durante l'avvio.
     *
     * @param stage finestra principale fornita da JavaFX
     */
    @Override
    public void start(Stage stage) {
        EncounterFactory encounterFactory = new EncounterFactory();
        SaveRepository repository = new SaveManager();
        SaveDataValidator validator = new SaveDataValidator(encounterFactory);
        GameSaver saver = new GameSaver();
        SaveLoader loader = new SaveLoader(encounterFactory);

        GameSaveService saveService = new GameSaveService(repository, validator, saver, loader);
        SceneManager sceneManager = new SceneManager(stage, encounterFactory, saveService);

        stage.setTitle("Robin Hood RPG");
        sceneManager.showMenu();
        stage.show();
    }

    /**
     * Avvia il ciclo di vita JavaFX.
     *
     * @param args argomenti della riga di comando
     */
    public static void main(String[] args) {
        launch(args);
    }
}