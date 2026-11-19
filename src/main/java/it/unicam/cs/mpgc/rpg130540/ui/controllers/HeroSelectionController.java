package it.unicam.cs.mpgc.rpg130540.ui.controllers;

import it.unicam.cs.mpgc.rpg130540.model.heroes.Hero;
import it.unicam.cs.mpgc.rpg130540.model.heroes.LittleJohn;
import it.unicam.cs.mpgc.rpg130540.model.heroes.RobinHood;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.function.Consumer;

/**
 * Mostra le caratteristiche degli eroi e gestisce la scelta.
 */
public class HeroSelectionController {

    private final Hero robinHood = new RobinHood();
    private final Hero littleJohn = new LittleJohn();
    private Runnable onBackToMenu;

    private Consumer<Hero> onHeroSelected;

    @FXML
    private Label robinName;

    @FXML
    private Label robinLife;

    @FXML
    private Label robinAttack;

    @FXML
    private Label robinAbility;

    @FXML
    private Label robinDefense;

    @FXML
    private Label johnName;

    @FXML
    private Label johnLife;

    @FXML
    private Label johnAttack;

    @FXML
    private Label johnAbility;

    @FXML
    private Label johnDefense;

    @FXML
    private void initialize() {
        showHero(
                this.robinHood,
                this.robinName,
                this.robinLife,
                this.robinAttack,
                this.robinAbility,
                this.robinDefense
        );

        showHero(
                this.littleJohn,
                this.johnName,
                this.johnLife,
                this.johnAttack,
                this.johnAbility,
                this.johnDefense
        );
    }

    private void showHero(
            Hero hero,
            Label name,
            Label life,
            Label attack,
            Label ability,
            Label defense
    ) {
        name.setText(hero.getName());
        life.setText("Punti vita: " + hero.getLifePoints());
        attack.setText("Attacco base: " + hero.getAttack());

        ability.setText(
                "Abilità: " + hero.getSpecialAbility().getName()
        );

        defense.setText(
                "Difesa: " + hero.getSpecialDefense().getName()
        );
    }

    public void configure(Consumer<Hero> onHeroSelected, Runnable onBackToMenu) {
        if (onHeroSelected == null || onBackToMenu == null) {
            throw new IllegalArgumentException(
                    "Le azioni della schermata sono obbligatorie"
            );
        }

        this.onHeroSelected = onHeroSelected;
        this.onBackToMenu = onBackToMenu;
    }

    @FXML
    private void onChooseRobin() {
        this.onHeroSelected.accept(this.robinHood);
    }

    @FXML
    private void onChooseJohn() {
        this.onHeroSelected.accept(this.littleJohn);
    }

    @FXML
    private void onBackToMenu() {
        this.onBackToMenu.run();
    }
}