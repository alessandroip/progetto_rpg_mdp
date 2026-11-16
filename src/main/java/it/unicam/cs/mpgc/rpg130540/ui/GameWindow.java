package it.unicam.cs.mpgc.rpg130540.ui;

import it.unicam.cs.mpgc.rpg130540.game.GameSession;
import it.unicam.cs.mpgc.rpg130540.game.enumerators.Phase;
import it.unicam.cs.mpgc.rpg130540.game.enumerators.TurnPhase;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;

public class GameWindow {
    private final GameSession session;

    private final Label levelLabel = new Label();
    private final Label heroLabel = new Label();
    private final Label enemyLabel = new Label();
    private final Label rewardsLabel = new Label();
    private final Label messageLabel = new Label();

    private final Button attackButton = new Button("Attacca");
    private final Button abilityButton = new Button();
    private final Button defenseButton = new Button();
    private final Button receiveButton = new Button("Incassa il colpo");
    private final Button donateButton = new Button("Dona il bottino");
    private final Button nextButton = new Button("Prossimo incontro");

    private final ListView<String> inventoryList = new ListView<>();
    private final Button useItemButton = new Button("Usa oggetto selezionato");

    private final ChoiceBox<String> rewardChoice = new ChoiceBox<>();
    private final Button chooseRewardButton = new Button("Ricevi ricompensa");

    public GameWindow(GameSession session) {
        this.session = session;
    }

    public Scene createScene() {
        this.messageLabel.setWrapText(true);

        VBox information = new VBox(12, this.levelLabel, this.heroLabel, this.enemyLabel, this.rewardsLabel, this.messageLabel);

        this.inventoryList.setPrefHeight(100);
        this.rewardChoice.setMaxWidth(Double.MAX_VALUE);

        information.getChildren().addAll(
                new Label("Inventario"),
                this.inventoryList,
                new Label("Scegli la ricompensa"),
                this.rewardChoice
        );

        GridPane actions = new GridPane();
        actions.setHgap(12);
        actions.setVgap(12);
        actions.add(this.attackButton, 0, 0);
        actions.add(this.abilityButton, 1, 0);
        actions.add(this.defenseButton, 0, 1);
        actions.add(this.receiveButton, 1, 1);
        actions.add(this.donateButton, 0, 2);
        actions.add(this.nextButton, 1, 2);
        actions.add(this.useItemButton, 0, 3);
        actions.add(this.chooseRewardButton, 1, 3);

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(24));
        root.setCenter(information);
        root.setBottom(actions);

        this.attackButton.setOnAction(event -> {
            this.session.attack();
            refresh();
        });

        this.abilityButton.setOnAction(event -> {
            this.session.useSpecialAbility();
            refresh();
        });

        this.defenseButton.setOnAction(event -> {
            this.session.useSpecialDefense();
            refresh();
        });

        this.receiveButton.setOnAction(event -> {
            this.session.receiveAttack();
            refresh();
        });

        this.donateButton.setOnAction(event -> {
            this.session.donateLoot();
            refresh();
        });

        this.nextButton.setOnAction(event -> {
            this.session.startNextEncounter();
            refresh();
        });

        this.useItemButton.setOnAction(event -> {
            int index = this.inventoryList.getSelectionModel().getSelectedIndex();
            boolean used = this.session.useArtifact(index);

            refresh();

            if (!used) {
                this.messageLabel.setText(
                        "Seleziona un oggetto utilizzabile. La cura richiede PV mancanti."
                );
            }
        });

        this.chooseRewardButton.setOnAction(event -> {
            int index = this.rewardChoice.getSelectionModel().getSelectedIndex();
            this.session.chooseReward(index);
            refresh();
        });

        refresh();
        return new Scene(root, 800, 620);
    }

    private void refresh() {
        this.levelLabel.setText("Livello " + this.session.getCurrentLevel());
        this.heroLabel.setText(this.session.getHeroName() + " - PV: " + this.session.getHeroLifePoints());
        this.enemyLabel.setText(this.session.getEnemyName() + " - PV: " + this.session.getEnemyLifePoints());
        this.rewardsLabel.setText("Onore: " + this.session.getHonor() + " - Bottino da donare: " + this.session.getPendingCoins());
        this.abilityButton.setText(this.session.getAbilityName());
        this.defenseButton.setText(this.session.getDefenseName());

        Phase phase = this.session.getPhase();

        boolean choosingAction = phase == Phase.FIGHTING && this.session.getTurnPhase() == TurnPhase.HERO_ACTION;
        boolean choosingDefense = phase == Phase.FIGHTING && this.session.getTurnPhase() == TurnPhase.HERO_DEFENSE;

        this.inventoryList.getItems().setAll(this.session.getInventoryNames());

        if (!this.inventoryList.getItems().isEmpty()) {
            this.inventoryList.getSelectionModel().selectFirst();
        }

        this.rewardChoice.getItems().setAll(this.session.getRewardNames());

        if (!this.rewardChoice.getItems().isEmpty()) {
            this.rewardChoice.getSelectionModel().selectFirst();
        }

        boolean choosingReward = phase == Phase.REWARD_PENDING;
        boolean canUseItems = choosingAction || phase == Phase.BETWEEN_ENCOUNTERS;

        this.useItemButton.setDisable(
                !canUseItems || this.inventoryList.getItems().isEmpty()
        );
        this.chooseRewardButton.setDisable(!choosingReward);
        this.rewardChoice.setDisable(!choosingReward);

        this.attackButton.setDisable(!choosingAction);
        this.abilityButton.setDisable(!this.session.canUseSpecialAbility());

        this.defenseButton.setDisable(!this.session.canUseSpecialDefense());
        this.receiveButton.setDisable(!choosingDefense);

        this.donateButton.setDisable(phase != Phase.DONATION_PENDING);

        this.nextButton.setDisable(phase != Phase.BETWEEN_ENCOUNTERS);

        if (phase == Phase.COMPLETED) {
            this.messageLabel.setText("Hai sconfitto il re e donato il tesoro!");
        } else if (phase == Phase.RETRY_PENDING) {
            this.messageLabel.setText("Sei stato sconfitto.");
        } else if (phase == Phase.DONATION_PENDING) {
            this.messageLabel.setText("Hai vinto! Dona il bottino al villaggio.");
        } else if (phase == Phase.REWARD_PENDING) {
            this.messageLabel.setText("Scegli il manufatto offerto dal villaggio.");
        } else if (phase == Phase.BETWEEN_ENCOUNTERS) {
            this.messageLabel.setText("Donazione conclusa. Puoi proseguire.");
        } else if (choosingDefense) {
            this.messageLabel.setText(this.session.getPendingAttackName() + " - " + this.session.getPendingDamage() + " danni. Scegli la difesa.");
        } else {
            this.messageLabel.setText("Scegli un attacco o la tua abilità.");
        }
    }
}
