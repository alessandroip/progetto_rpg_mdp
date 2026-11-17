package it.unicam.cs.mpgc.rpg130540.ui.sprites;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;

/**
 * Crea le immagini degli eroi da mostrare nell'interfaccia.
 */
public class HeroSpriteFactory {

    /**
     * Prepara lo sprite dell'eroe richiesto.
     *
     * @param heroName nome dell'eroe
     * @return una nuova vista contenente lo sprite
     * @throws IllegalArgumentException se l'eroe non è riconosciuto
     * @throws IllegalStateException se il file dell'immagine non esiste
     */
    public ImageView createSprite(String heroName) {
        String imagePath;

        switch (heroName) {
            case "Robin Hood":
                imagePath = "/images/heroes/robin-hood.png";
                break;
            case "Little John":
                imagePath = "/images/heroes/little-john.png";
                break;
            default:
                throw new IllegalArgumentException("Eroe non riconosciuto");
        }

        URL resource = getClass().getResource(imagePath);

        if (resource == null) {
            throw new IllegalStateException(
                    "Immagine non trovata: " + imagePath
            );
        }

        Image image = new Image(resource.toExternalForm());
        ImageView sprite = new ImageView(image);

        sprite.setFitHeight(168);
        sprite.setPreserveRatio(true);
        sprite.setSmooth(false);

        return sprite;
    }
}