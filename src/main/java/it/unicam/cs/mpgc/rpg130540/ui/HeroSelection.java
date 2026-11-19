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
 * Carica la schermata di selezione dell'eroe.
 */
public class HeroSelection {

    public Scene createScene(Consumer<Hero> onHeroSelected, Runnable onBackToMenu) {
        URL resource = getClass().getResource(
                "/fxml/HeroSelection.fxml"
        );

        if (resource == null) {
            throw new IllegalStateException(
                    "File /fxml/HeroSelection.fxml non trovato"
            );
        }

        FXMLLoader loader = new FXMLLoader(resource);

        try {
            Parent root = loader.load();

            HeroSelectionController controller =
                    loader.getController();

            controller.configure(onHeroSelected, onBackToMenu);

            return new Scene(root, 750, 600);

        } catch (IOException exception) {
            throw new IllegalStateException(
                    "Impossibile caricare la selezione dell'eroe",
                    exception
            );
        }
    }
}