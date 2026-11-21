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
 * Carica la scena FXML della partita e passa sessione, servizio di salvataggio
 * e azioni finali al controller. Non crea una finestra Stage separata.
 */
public class GameWindow {
    /**
     * Sessione della partita visualizzata.
     */
    private final GameSession session;
    /**
     * Servizio di salvataggio e caricamento.
     */
    private final GameSaveService saveService;
    /**
     * Azione che mostra la sconfitta.
     */
    private final Runnable onGameOver;
    /**
     * Azione che mostra la vittoria.
     */
    private final Runnable onVictory;
    /**
     * Azione da passare al controller per abbandonare la partita.
     */
    private final Runnable onAbandon;

    /**
     * Prepara i componenti necessari al caricamento della schermata di gioco.
     *
     * @param session partita da visualizzare, non nulla
     * @param saveService servizio di salvataggio, non nullo
     * @param onGameOver azione che mostra la sconfitta, non nulla
     * @param onVictory azione che mostra la vittoria, non nulla
     * @param onAbandon azione che torna al menu dopo l'abbandono, non nulla
     * @throws IllegalArgumentException se un componente richiesto è nullo
     */
    public GameWindow(GameSession session, GameSaveService saveService, Runnable onGameOver, Runnable onVictory, Runnable onAbandon) {
        if (session == null || saveService == null || onGameOver == null || onVictory == null || onAbandon == null) {
            throw new IllegalArgumentException("I componenti della schermata sono obbligatori");
        }
        this.session = session;
        this.saveService = saveService;
        this.onGameOver = onGameOver;
        this.onVictory = onVictory;
        this.onAbandon = onAbandon;
    }

    /**
     * Carica Game.fxml e configura il controller con sessione, salvataggio e transizioni finali.
     *
     * @return nuova scena JavaFX della schermata
     * @throws IllegalStateException se FXML o immagine richiesta mancano oppure il caricamento fallisce
     */
    public Scene createScene() {
        URL resource = getClass().getResource("/fxml/Game.fxml");

        if (resource == null) {
            throw new IllegalStateException("File /fxml/Game.fxml non trovato");
        }

        FXMLLoader loader = new FXMLLoader(resource);

        try {
            Parent root = loader.load();

            GameController controller = loader.getController();

            controller.configure(this.session, this.saveService, this.onGameOver, this.onVictory, this.onAbandon);

            return new Scene(root, 1200, 720);

        } catch (IOException exception) {
            throw new IllegalStateException("Impossibile caricare la schermata di gioco", exception);
        }
    }
}