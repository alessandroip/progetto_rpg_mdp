package it.unicam.cs.mpgc.rpg130540.game;

import it.unicam.cs.mpgc.rpg130540.model.heroes.Hero;
import it.unicam.cs.mpgc.rpg130540.model.enemies.Enemy;
import it.unicam.cs.mpgc.rpg130540.game.enumerators.Phase;
import it.unicam.cs.mpgc.rpg130540.game.enumerators.TurnPhase;
import it.unicam.cs.mpgc.rpg130540.items.Artifact;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe GameSession che gestisce le fasi della partita e l'onore.
 */
public class GameSession {
    private Enemy enemy;
    private Fight fight;
    private Phase phase = Phase.FIGHTING;
    private final Rewards rewards = new Rewards();
    private final Hero hero;
    private final EncounterFactory encounterFactory;
    private Encounter currentEncounter;
    private int currentLevel = 1;
    private List<Artifact> rewardOptions = new ArrayList<>();

    public GameSession(Hero hero, EncounterFactory encounterFactory) {
        if (hero == null || encounterFactory == null) {
            throw new IllegalArgumentException("Eroe e factory sono obbligatori");
        }

        this.hero = hero;
        this.encounterFactory = encounterFactory;
        startCurrentEncounter();
    }

    public GameSession(
            Hero hero,
            EncounterFactory encounterFactory,
            int completedLevel,
            int savedHonor
    ) {
        if (hero == null || encounterFactory == null || !hero.isAlive()) {
            throw new IllegalArgumentException("Eroe o factory non validi");
        }

        if (completedLevel < 1
                || completedLevel >= encounterFactory.getLastLevel()) {
            throw new IllegalArgumentException(
                    "Il salvataggio deve riferirsi a un punto tra gli incontri"
            );
        }

        if (savedHonor != completedLevel) {
            throw new IllegalArgumentException(
                    "Onore e livello completato non sono coerenti"
            );
        }

        this.hero = hero;
        this.encounterFactory = encounterFactory;
        this.currentLevel = completedLevel;

        this.currentEncounter =
                this.encounterFactory.createEncounter(this.currentLevel);
        this.enemy = this.currentEncounter.getEnemy();

        this.enemy.takeDamage(this.enemy.getLifePoints());
        this.fight = new Fight(this.hero, this.enemy);

        this.rewards.restoreHonor(savedHonor);
        this.phase = Phase.BETWEEN_ENCOUNTERS;
    }

    private void startCurrentEncounter() {
        this.currentEncounter =
                this.encounterFactory.createEncounter(this.currentLevel);

        this.enemy = this.currentEncounter.getEnemy();
        this.fight = new Fight(this.hero, this.enemy);
        this.phase = Phase.FIGHTING;
    }

    /**
     * Il metodo attack() esegue un turno se la sessione è in fase FIGHTING.
     *
     * Se l'eroe vince, assegna un punto onore e il bottino dell'incontro, quindi passa a DONATION_PENDIING.
     * Se l'eroe perde, passa a RETRY_PENDING.
     * Se lo scontro prosegue, la fase resta FIGHTING.
     *
     * Nelle altre fasi, la chiamata non ha effetto.
     */
    public void attack() {
        if (phase != Phase.FIGHTING) {
            return;
        }
        this.fight.attack();
        updateFightOutcome();
    }

    public void useSpecialAbility() {
        if (this.phase != Phase.FIGHTING) {
            return;
        }
        this.fight.useSpecialAbility();
        updateFightOutcome();
    }

    public void receiveAttack() {
        if (this.phase != Phase.FIGHTING) {
            return;
        }
        this.fight.receiveAttack();
        updateFightOutcome();
    }

    public void useSpecialDefense() {
        if (this.phase != Phase.FIGHTING) {
            return;
        }
        this.fight.useSpecialDefense();
        updateFightOutcome();
    }

    private void updateFightOutcome() {
        if (!this.fight.isEnded()) {
            return;
        }
        if (!this.enemy.isAlive()) {
            this.rewards.registerVictory(this.currentEncounter.getRewardCoins());
            this.phase = Phase.DONATION_PENDING;
        } else {
            this.phase = Phase.RETRY_PENDING;
        }
    }

    public TurnPhase getTurnPhase() {
        return this.fight.getTurnPhase();
    }

    public void donateLoot() {
        if (this.phase != Phase.DONATION_PENDING) {
            return;
        }

        this.rewards.donateLoot();

        if (this.currentLevel == this.encounterFactory.getLastLevel()) {
            this.phase = Phase.COMPLETED;
        } else {
            this.rewardOptions = this.rewards.createRewardOptions();
            this.phase = Phase.REWARD_PENDING;
        }
    }

    /**
     * Avvia l'incontro successivo quando la donazione è conclusa e rimangono avversari da affrontare.
     */
    public void startNextEncounter() {
        if (this.phase != Phase.BETWEEN_ENCOUNTERS) {
            return;
        }

        this.currentLevel++;
        startCurrentEncounter();
    }

    public void chooseReward(int index) {
        if (this.phase != Phase.REWARD_PENDING) {
            return;
        }

        if (index < 0 || index >= this.rewardOptions.size()) {
            return;
        }

        this.hero.getInventory().add(this.rewardOptions.get(index));
        this.rewardOptions.clear();
        this.phase = Phase.BETWEEN_ENCOUNTERS;
    }

    public List<String> getRewardNames() {
        List<String> names = new ArrayList<>();

        for (Artifact artifact : this.rewardOptions) {
            names.add(artifact.getName());
        }

        return names;
    }

    public List<String> getInventoryNames() {
        return this.hero.getInventory().getItemNames();
    }

    public boolean useArtifact(int index) {
        if (this.phase == Phase.FIGHTING) {
            boolean used = this.fight.useArtifact(index);
            updateFightOutcome();
            return used;
        }

        if (this.phase == Phase.BETWEEN_ENCOUNTERS) {
            return this.hero.getInventory().use(index, this.hero);
        }

        return false;
    }

    /**
     * Metodo getter che restituisce la fase corrente della sessione.
     *
     * @return la fase che determina quali azioni sono disponibili
     */
    public Phase getPhase() {
        return phase;
    }

    /**
     * Metodo getter che restituisce il totale dei punti onore accumulati nella sessione.
     *
     * @return i punti onore ottenuti vincendo gli incontri
     */
    public int getHonor() {
        return this.rewards.getHonor();
    }

    /**
     * Metodo getter che determina il bottino non donato.
     *
     * @return le monete del bottino ancora da donare
     */
    public int getPendingCoins() {
        return this.rewards.getPendingCoins();
    }

    public String getHeroName() {
        return this.hero.getName();
    }

    public int getHeroLifePoints() {
        return this.hero.getLifePoints();
    }

    public String getEnemyName() {
        return this.enemy.getName();
    }

    public int getEnemyLifePoints() {
        return this.enemy.getLifePoints();
    }

    public int getCurrentLevel() {
        return this.currentLevel;
    }

    public String getAbilityName() {
        return this.hero.getSpecialAbility().getName();
    }

    public String getDefenseName() {
        return this.hero.getSpecialDefense().getName();
    }

    public String getPendingAttackName() {
        return this.fight.getPendingAttackName();
    }

    public int getPendingDamage() {
        return this.fight.getPendingDamage();
    }

    public boolean canUseSpecialAbility() {
        return this.phase == Phase.FIGHTING && this.fight.canUseSpecialAbility();
    }

    public boolean canUseSpecialDefense() {
        return this.phase == Phase.FIGHTING && this.fight.canUseSpecialDefense();
    }

    public boolean canSave() {
        return this.phase == Phase.BETWEEN_ENCOUNTERS;
    }

    public int getHeroAttack() {
        return this.hero.getAttack();
    }

    public List<Artifact> getInventoryItems() {
        return this.hero.getInventory().copyItems();
    }
}
