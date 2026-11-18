package it.unicam.cs.mpgc.rpg130540.ui.controllers;

import it.unicam.cs.mpgc.rpg130540.game.GameSession;
import it.unicam.cs.mpgc.rpg130540.game.enumerators.Phase;
import it.unicam.cs.mpgc.rpg130540.game.enumerators.TurnPhase;
import it.unicam.cs.mpgc.rpg130540.persistence.GameSaveService;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;

/**
 * Collega la schermata di gioco alla sessione e al servizio
 * di salvataggio, aggiornando informazioni e azioni disponibili.
 */
public class GameController {

    private GameSession session;
    private GameSaveService saveService;
    private Runnable onGameOver;
    private Runnable onVictory;
    private boolean endingShown;

    @FXML
    private ImageView heroImage;

    @FXML
    private Label heroNameLabel;

    @FXML
    private Label levelLabel;

    @FXML
    private Label heroLabel;

    @FXML
    private Label enemyLabel;

    @FXML
    private Label rewardsLabel;

    @FXML
    private Label messageLabel;

    @FXML
    private ListView<String> inventoryList;

    @FXML
    private ChoiceBox<String> rewardChoice;

    @FXML
    private Button attackButton;

    @FXML
    private Button abilityButton;

    @FXML
    private Button defenseButton;

    @FXML
    private Button receiveButton;

    @FXML
    private Button donateButton;

    @FXML
    private Button nextButton;

    @FXML
    private Button useItemButton;

    @FXML
    private Button chooseRewardButton;

    @FXML
    private Button saveButton;

    /**
     * Collega la schermata ai componenti della partita.
     * Va chiamato dopo il caricamento dell'FXML.
     */
    public void configure(
            GameSession session,
            GameSaveService saveService,
            Runnable onGameOver,
            Runnable onVictory
    ) {
        if (session == null || saveService == null
                || onGameOver == null || onVictory == null) {
            throw new IllegalArgumentException(
                    "I componenti della schermata sono obbligatori"
            );
        }

        this.session = session;
        this.saveService = saveService;
        this.onGameOver = onGameOver;
        this.onVictory = onVictory;
        this.endingShown = false;

        loadHeroImage();
        refresh();
    }

    /**
     * Carica l'immagine statica dell'eroe scelto.
     */
    private void loadHeroImage() {
        String imagePath;

        switch (this.session.getHeroName()) {
            case "Robin Hood":
                imagePath = "/images/heroes/robin-hood.png";
                break;

            case "Little John":
                imagePath = "/images/heroes/little-john.png";
                break;

            default:
                throw new IllegalArgumentException(
                        "Immagine dell'eroe non disponibile"
                );
        }

        URL resource = getClass().getResource(imagePath);

        if (resource == null) {
            throw new IllegalStateException(
                    "Immagine non trovata: " + imagePath
            );
        }

        this.heroImage.setImage(
                new Image(resource.toExternalForm())
        );
    }

    @FXML
    private void onAttack() {
        this.session.attack();
        refresh();
    }

    @FXML
    private void onUseAbility() {
        this.session.useSpecialAbility();
        refresh();
    }

    @FXML
    private void onReceiveAttack() {
        this.session.receiveAttack();
        refresh();
    }

    @FXML
    private void onUseDefense() {
        this.session.useSpecialDefense();
        refresh();
    }

    @FXML
    private void onDonate() {
        this.session.donateLoot();
        refresh();
    }

    @FXML
    private void onNextEncounter() {
        this.session.startNextEncounter();
        refresh();
    }

    @FXML
    private void onChooseReward() {
        int index = this.rewardChoice
                .getSelectionModel().getSelectedIndex();

        this.session.chooseReward(index);
        refresh();
    }

    @FXML
    private void onUseItem() {
        int index = this.inventoryList
                .getSelectionModel().getSelectedIndex();

        boolean used = this.session.useArtifact(index);

        refresh();

        if (!used) {
            this.messageLabel.setText(
                    "Seleziona un oggetto utilizzabile. "
                            + "La cura richiede PV mancanti."
            );
        }
    }

    @FXML
    private void onSave() {
        try {
            this.saveService.save(this.session);
            this.messageLabel.setText("Partita salvata.");

        } catch (IOException | IllegalArgumentException
                 | IllegalStateException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(
                    this.saveButton.getScene().getWindow()
            );
            alert.setTitle("Salvataggio");
            alert.setHeaderText(
                    "Impossibile salvare la partita"
            );
            alert.setContentText(exception.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Aggiorna testi, inventario, ricompense e pulsanti
     * in base allo stato attuale della sessione.
     */
    private void refresh() {
        this.heroNameLabel.setText(this.session.getHeroName());

        this.levelLabel.setText(
                "Livello " + this.session.getCurrentLevel()
        );

        this.heroLabel.setText(
                this.session.getHeroName()
                        + " - PV: " + this.session.getHeroLifePoints()
        );

        this.enemyLabel.setText(
                this.session.getEnemyName()
                        + " - PV: " + this.session.getEnemyLifePoints()
        );

        this.rewardsLabel.setText(
                "Onore: " + this.session.getHonor()
                        + " - Bottino da donare: "
                        + this.session.getPendingCoins()
        );

        this.abilityButton.setText(
                this.session.getAbilityName()
        );

        this.defenseButton.setText(
                this.session.getDefenseName()
        );

        Phase phase = this.session.getPhase();

        boolean choosingAction =
                phase == Phase.FIGHTING
                        && this.session.getTurnPhase()
                        == TurnPhase.HERO_ACTION;

        boolean choosingDefense =
                phase == Phase.FIGHTING
                        && this.session.getTurnPhase()
                        == TurnPhase.HERO_DEFENSE;

        boolean choosingReward =
                phase == Phase.REWARD_PENDING;

        boolean canUseItems =
                choosingAction
                        || phase == Phase.BETWEEN_ENCOUNTERS;

        refreshInventory();
        refreshRewardOptions();

        this.attackButton.setDisable(!choosingAction);

        this.abilityButton.setDisable(
                !choosingAction
                        || !this.session.canUseSpecialAbility()
        );

        this.receiveButton.setDisable(!choosingDefense);

        this.defenseButton.setDisable(
                !choosingDefense
                        || !this.session.canUseSpecialDefense()
        );

        this.donateButton.setDisable(
                phase != Phase.DONATION_PENDING
        );

        this.nextButton.setDisable(
                phase != Phase.BETWEEN_ENCOUNTERS
        );

        this.useItemButton.setDisable(
                !canUseItems
                        || this.inventoryList.getItems().isEmpty()
        );

        this.chooseRewardButton.setDisable(
                !choosingReward
                        || this.rewardChoice.getItems().isEmpty()
        );

        this.rewardChoice.setDisable(!choosingReward);

        this.saveButton.setDisable(
                !this.session.canSave()
        );

        updateMessage(phase, choosingDefense);

        showEndingIfNeeded(phase);
    }

    private void refreshInventory() {
        int selectedIndex = this.inventoryList
                .getSelectionModel().getSelectedIndex();

        this.inventoryList.getItems().setAll(
                this.session.getInventoryNames()
        );

        if (!this.inventoryList.getItems().isEmpty()) {
            int index = Math.max(
                    0,
                    Math.min(
                            selectedIndex,
                            this.inventoryList.getItems().size() - 1
                    )
            );

            this.inventoryList.getSelectionModel().select(index);
        }
    }

    private void refreshRewardOptions() {
        int selectedIndex = this.rewardChoice
                .getSelectionModel().getSelectedIndex();

        this.rewardChoice.getItems().setAll(
                this.session.getRewardNames()
        );

        if (!this.rewardChoice.getItems().isEmpty()) {
            int index = Math.max(
                    0,
                    Math.min(
                            selectedIndex,
                            this.rewardChoice.getItems().size() - 1
                    )
            );

            this.rewardChoice.getSelectionModel().select(index);
        }
    }

    private void updateMessage(
            Phase phase,
            boolean choosingDefense
    ) {
        if (phase == Phase.COMPLETED) {
            this.messageLabel.setText(
                    "Hai sconfitto il re e donato il tesoro!"
            );

        } else if (phase == Phase.RETRY_PENDING) {
            this.messageLabel.setText(
                    "Sei stato sconfitto."
            );

        } else if (phase == Phase.DONATION_PENDING) {
            this.messageLabel.setText(
                    "Hai vinto! Dona il bottino al villaggio."
            );

        } else if (phase == Phase.REWARD_PENDING) {
            this.messageLabel.setText(
                    "Scegli il manufatto offerto dal villaggio."
            );

        } else if (phase == Phase.BETWEEN_ENCOUNTERS) {
            this.messageLabel.setText(
                    "Donazione conclusa. Puoi proseguire."
            );

        } else if (choosingDefense) {
            this.messageLabel.setText(
                    this.session.getPendingAttackName()
                            + " - "
                            + this.session.getPendingDamage()
                            + " danni. Scegli la difesa."
            );

        } else {
            this.messageLabel.setText(
                    "Scegli un attacco o la tua abilità."
            );
        }
    }

    /**
     * Mostra una sola volta la schermata relativa all'esito finale.
     */
    private void showEndingIfNeeded(Phase phase) {
        if (this.endingShown) {
            return;
        }

        if (phase == Phase.RETRY_PENDING) {
            this.endingShown = true;
            this.onGameOver.run();

        } else if (phase == Phase.COMPLETED) {
            this.endingShown = true;
            this.onVictory.run();
        }
    }
}