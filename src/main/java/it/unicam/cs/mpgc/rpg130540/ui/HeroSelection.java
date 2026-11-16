package it.unicam.cs.mpgc.rpg130540.ui;

import it.unicam.cs.mpgc.rpg130540.model.heroes.Hero;
import it.unicam.cs.mpgc.rpg130540.model.heroes.LittleJohn;
import it.unicam.cs.mpgc.rpg130540.model.heroes.RobinHood;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.function.Consumer;

public class HeroSelection {

    public Scene createScene(Consumer<Hero> onHeroSelected) {
        Hero robinHood = new RobinHood();
        Hero littleJohn = new LittleJohn();

        VBox robinCard = createHeroCard(robinHood, onHeroSelected);
        VBox johnCard = createHeroCard(littleJohn, onHeroSelected);

        Label title = new Label("Scegli il tuo eroe");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        HBox cards = new HBox(30, robinCard, johnCard);

        VBox root = new VBox(24, title, cards);
        root.setPadding(new Insets(24));

        return new Scene(root, 750, 400);
    }

    private VBox createHeroCard(
            Hero hero,
            Consumer<Hero> onHeroSelected
    ) {
        Label name = new Label(hero.getName());
        Label life = new Label("Punti vita: " + hero.getLifePoints());
        Label attack = new Label("Attacco base: " + hero.getAttack());
        Label ability = new Label(
                "Abilità: " + hero.getSpecialAbility().getName()
        );
        Label defense = new Label(
                "Difesa: " + hero.getSpecialDefense().getName()
        );
        Label uses = new Label(
                "Abilità e difesa: un uso ciascuna per incontro."
        );
        uses.setWrapText(true);

        Button chooseButton = new Button("Scegli " + hero.getName());
        chooseButton.setOnAction(event -> onHeroSelected.accept(hero));

        VBox card = new VBox(
                12, name, life, attack, ability, defense, uses, chooseButton
        );
        card.setPrefWidth(330);
        card.setPadding(new Insets(16));
        card.setStyle("-fx-border-color: #888; -fx-border-radius: 6;");

        return card;
    }
}