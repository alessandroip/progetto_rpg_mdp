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
 * Classe SceneManager coordina la navigazione fra menu, scelta dell'eroe, partita e schermate finali. Delega la
 * creazione della partita e il caricamento ai componenti dedicati, mantenendo il modello
 * indipendente da JavaFX.
 */
public class SceneManager {
    /**
     * Finestra principale condivisa dalle schermate.
     */
    private final Stage stage;
    /**
     * Factory degli incontri.
     */
    private final EncounterFactory encounterFactory;
    /**
     * Servizio di salvataggio e caricamento.
     */
    private final GameSaveService saveService;

    /**
     * Il costruttore collega la finestra principale ai servizi necessari alla navigazione.
     *
     * @param stage finestra principale fornita da JavaFX, non nulla
     * @param encounterFactory factory usata per creare gli incontri, non nulla
     * @param saveService servizio di salvataggio, non nullo
     * @throws IllegalArgumentException se almeno una dipendenza è nulla
     */
    public SceneManager(Stage stage, EncounterFactory encounterFactory, GameSaveService saveService) {
        if (stage == null || encounterFactory == null || saveService == null) {
            throw new IllegalArgumentException("Finestra, factory e servizio sono obbligatori");
        }
        this.stage = stage;
        this.encounterFactory = encounterFactory;
        this.saveService = saveService;
    }

    /**
     * Carica il menu principale e collega nuova partita e caricamento.
     *
     * @throws IllegalStateException se le risorse della schermata non sono disponibili
     *                               o non sono caricabili
     */
    public void showMenu() {
        MainMenu menu = new MainMenu();

        this.stage.setScene(menu.createScene(() -> showHeroSelection(), () -> loadGame()));
    }

    /**
     * Mostra una nuova selezione dell'eroe. La scelta crea una sessione dal
     * primo livello; Il ritorno riapre il menu senza modificare il salvataggio.
     *
     * @throws IllegalStateException se le risorse della schermata non sono disponibili
     *                               o non sono caricabili
     */
    public void showHeroSelection() {
        HeroSelection selection = new HeroSelection();

        this.stage.setScene(selection.createScene(hero -> {
                    GameSession session = new GameSession(hero, this.encounterFactory);
                    showGame(session);
                    },
                () -> showMenu()));
    }

    /**
     * Mostra la schermata della partita indicata.
     *
     * Collega il controller alle schermate di sconfitta e vittoria
     * e al ritorno al menu in caso di abbandono.
     *
     * @param session partita da visualizzare, non nulla
     * @throws IllegalArgumentException se la sessione è nulla
     * @throws IllegalStateException se il caricamento delle risorse
     *                               della schermata fallisce
     */
    public void showGame(GameSession session) {
        GameWindow window = new GameWindow(session, this.saveService, () -> showGameOver(), () -> showVictory(), () -> showMenu());
        this.stage.setScene(window.createScene());
    }

    /**
     * Chiede al servizio di caricare la partita e ne mostra la schermata.
     * In caso di IOException visualizza un avviso e conserva la schermata precedente.
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

    /**
     * Mostra Game Over, collegando ricomincia alla scelta dell'eroe e il ritorno
     * al menu. Non cancella il salvataggio.
     *
     * @throws IllegalStateException se le risorse della schermata non sono disponibili
     *                               o non sono caricabili
     */
    public void showGameOver() {
        FXMLLoader loader = createLoader("GameOver.fxml");

        try {
            Parent root = loader.load();

            GameOverController controller = loader.getController();

            controller.configure(() -> showHeroSelection(), () -> showMenu());

            this.stage.setScene(new Scene(root, 750, 400));

        } catch (IOException exception) {
            throw new IllegalStateException("Impossibile caricare Game Over", exception);
        }
    }

    /**
     * Mostra la schermata di vittoria e collega il ritorno al menu.
     *
     * @throws IllegalStateException se le risorse della schermata non sono disponibili
     *                               o non sono caricabili
     */
    public void showVictory() {
        FXMLLoader loader = createLoader("Victory.fxml");

        try {
            Parent root = loader.load();

            VictoryController controller = loader.getController();

            controller.configure(() -> showMenu());

            this.stage.setScene(new Scene(root, 750, 400));

        } catch (IOException exception) {
            throw new IllegalStateException("Impossibile caricare la vittoria", exception);
        }
    }

    /**
     * Cerca un file nella cartella di risorse fxml e prepara il caricatore senza caricare
     * ancora la scena.
     *
     * @param fileName nome del file FXML
     * @return caricatore associato alla risorsa
     * @throws IllegalStateException se il file non è presente nelle risorse
     */
    private FXMLLoader createLoader(String fileName) {
        URL resource = getClass().getResource("/fxml/" + fileName);

        if (resource == null) {
            throw new IllegalStateException("Schermata non trovata: " + fileName);
        }
        return new FXMLLoader(resource);
    }
}