package it.unicam.cs.mpgc.rpg130540.ui;

import it.unicam.cs.mpgc.rpg130540.game.Encounter;
import it.unicam.cs.mpgc.rpg130540.game.EncounterFactory;
import it.unicam.cs.mpgc.rpg130540.game.GameSession;
import it.unicam.cs.mpgc.rpg130540.persistence.GameSaveService;
import it.unicam.cs.mpgc.rpg130540.ui.controllers.GameOverController;
import it.unicam.cs.mpgc.rpg130540.ui.controllers.VictoryController;


import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.net.URL;
import java.io.IOException;

/**
 * Gestisce la navigazione tra le schermate dell'applicazione.
 */
public class SceneManager {
    private final Stage stage;
    private final EncounterFactory encounterFactory;
    private final GameSaveService saveService;

    public SceneManager(Stage stage, EncounterFactory encounterFactory, GameSaveService saveService) {
        if (stage == null || encounterFactory == null || saveService == null) {
            throw new IllegalArgumentException("Finestra, factory e servizio sono obbligatori");
        }
        this.stage = stage;
        this.encounterFactory = encounterFactory;
        this.saveService = saveService;
    }

    /**
     * Mostra il menu principale e collega le sue azioni.
     */
    public void showMenu() {
        MainMenu menu = new MainMenu();

        this.stage.setScene(menu.createScene(() -> showHeroSelection(), () -> loadGame()));
    }

    /**
     * Mostra la scelta dell'eroe e avvia una nuova sessione.
     */
    public void showHeroSelection() {
        HeroSelection selection = new HeroSelection();

        this.stage.setScene(selection.createScene(hero -> {
            GameSession session = new GameSession(hero, this.encounterFactory);
            showGame(session);
        }));
    }

    /**
     * Mostra la partita indicata.
     *
     * @param session sessione da visualizzare
     */
    public void showGame(GameSession session) {
        GameWindow window = new GameWindow(
                session,
                this.saveService,
                () -> showGameOver(),
                () -> showVictory()
        );

        this.stage.setScene(window.createScene());
    }

    /**
     * Carica una partita e ne mostra la schermata.
     */
    private void loadGame() {
        try {
            GameSession session = this.saveService.load();
            showGame(session);

        } catch (IOException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(this.stage);
            alert.setTitle("Caricamento");
            alert.setHeaderText("Impossibile caricare la partita");
            alert.setContentText(exception.getMessage());
            alert.showAndWait();
        }
    }

    public void showGameOver() {
        FXMLLoader loader = createLoader("GameOver.fxml");

        try {
            Parent root = loader.load();

            GameOverController controller =
                    loader.getController();

            controller.configure(
                    () -> showHeroSelection(),
                    () -> showMenu()
            );

            this.stage.setScene(new Scene(root, 750, 400));

        } catch (IOException exception) {
            throw new IllegalStateException(
                    "Impossibile caricare Game Over",
                    exception
            );
        }
    }

    public void showVictory() {
        FXMLLoader loader = createLoader("Victory.fxml");

        try {
            Parent root = loader.load();

            VictoryController controller =
                    loader.getController();

            controller.configure(() -> showMenu());

            this.stage.setScene(new Scene(root, 750, 400));

        } catch (IOException exception) {
            throw new IllegalStateException(
                    "Impossibile caricare la vittoria",
                    exception
            );
        }
    }

    private FXMLLoader createLoader(String fileName) {
        URL resource = getClass().getResource(
                "/fxml/" + fileName
        );

        if (resource == null) {
            throw new IllegalStateException(
                    "Schermata non trovata: " + fileName
            );
        }

        return new FXMLLoader(resource);
    }
}
