package it.unicam.cs.mpgc.rpg130540.ui;

import it.unicam.cs.mpgc.rpg130540.model.heroes.Hero;
import it.unicam.cs.mpgc.rpg130540.ui.controllers.HeroSelectionController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;
import java.util.function.Consumer;

/**
 * Carica la selezione dell'eroe e collega scelta del personaggio e ritorno al menu.
 * Le immagini statiche delle schede sono definite nel file FXML.
 */
public class HeroSelection {

    /**
     * Carica HeroSelection.fxml e configura scelta dell'eroe e ritorno al menu.
     *
     * @param onHeroSelected azione che riceve l'eroe scelto, non nulla
     * @param onBackToMenu azione di ritorno al menu, non nulla
     * @return nuova scena JavaFX della schermata
     * @throws IllegalStateException se la risorsa FXML manca o il suo caricamento fallisce
     * @throws IllegalArgumentException se le azioni richieste dal controller sono nulle
     */
    public Scene createScene(Consumer<Hero> onHeroSelected, Runnable onBackToMenu) {
        URL resource = getClass().getResource("/fxml/HeroSelection.fxml");

        if (resource == null) {
            throw new IllegalStateException("File /fxml/HeroSelection.fxml non trovato");
        }

        FXMLLoader loader = new FXMLLoader(resource);

        try {
            Parent root = loader.load();

            HeroSelectionController controller =
                    loader.getController();

            controller.configure(onHeroSelected, onBackToMenu);

            return new Scene(root, 750, 600);

        } catch (IOException exception) {
            throw new IllegalStateException("Impossibile caricare la selezione dell'eroe", exception);
        }
    }
}