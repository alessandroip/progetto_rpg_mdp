package it.unicam.cs.mpgc.rpg130540.game;

import it.unicam.cs.mpgc.rpg130540.model.heroes.Hero;
import it.unicam.cs.mpgc.rpg130540.model.enemies.Enemy;
import it.unicam.cs.mpgc.rpg130540.game.enumerators.Phase;
import it.unicam.cs.mpgc.rpg130540.game.enumerators.TurnPhase;
import it.unicam.cs.mpgc.rpg130540.items.Artifact;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe GameSession che coordina la progressione della partita, delegando:
 * - il combattimento a Fight,
 * - onore e bottino a Rewards.
 *
 * Conserva lo stesso eroe fra gli incontri e controlla donazione, scelta della
 * ricompensa, avanzamento e disponibilità del salvataggio.
 */
public class GameSession {
    /**
     * Nemico corrente associato al combattimento o incontro.
     */
    private Enemy enemy;
    /**
     * Combattimento corrente.
     */
    private Fight fight;
    /**
     * Fase complessiva della sessione.
     */
    private Phase phase = Phase.FIGHTING;
    /**
     * Gestore di onore e bottino della sessione.
     */
    private final Rewards rewards = new Rewards();
    /**
     * Eroe mantenuto nella partita.
     */
    private final Hero hero;
    /**
     * Factory degli incontri.
     */
    private final EncounterFactory encounterFactory;
    /**
     * Incontro corrente con nemico e bottino.
     */
    private Encounter currentEncounter;
    /**
     * Livello corrente, tra incontri indica il livello appena completato.
     */
    private int currentLevel = 1;
    /**
     * Manufatti disponibili per la scelta della ricompensa.
     */
    private List<Artifact> rewardOptions = new ArrayList<>();

    /**
     * Questo costruttore crea una nuova sessione dal primo livello con l'eroe fornito.
     * Non azzera l'inventario né ripristina automaticamente le statistiche dell'eroe ricevuto.
     *
     * @param hero eroe coinvolto, non nullo
     * @param encounterFactory factory usata per creare gli incontri, non nulla
     * @throws IllegalArgumentException se eroe o factory sono nulli
     */
    public GameSession(Hero hero, EncounterFactory encounterFactory) {
        if (hero == null || encounterFactory == null) {
            throw new IllegalArgumentException("Eroe e factory sono obbligatori");
        }
        this.hero = hero;
        this.encounterFactory = encounterFactory;
        startCurrentEncounter();
    }

    /**
     * Quest'altro costruttore ricostruisce una sessione tra due incontri. Crea il nemico
     * del livello già completato, lo marca come sconfitto e ripristina l'onore senza
     * assegnare di nuovo bottino o ricompense.
     *
     * @param hero eroe coinvolto, non nullo
     * @param encounterFactory factory usata per creare gli incontri, non nulla
     * @param completedLevel livello già completato, da uno al penultimo
     * @param savedHonor onore salvato, uguale al livello completato
     * @throws IllegalArgumentException se le dipendenze non sono valide, l'eroe è sconfitto
     *                                  o progressione e onore non sono coerenti
     */
    public GameSession(Hero hero, EncounterFactory encounterFactory, int completedLevel, int savedHonor) {
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
        this.currentEncounter = this.encounterFactory.createEncounter(this.currentLevel);
        this.enemy = this.currentEncounter.getEnemy();
        this.enemy.takeDamage(this.enemy.getLifePoints());
        this.fight = new Fight(this.hero, this.enemy);
        this.rewards.restoreHonor(savedHonor);
        this.phase = Phase.BETWEEN_ENCOUNTERS;
    }

    /**
     * Crea l'incontro del livello corrente e un nuovo combattimento, rendendo nuovamente
     * disponibili abilità e difesa. Mantiene lo stesso eroe, i suoi punti vita, l'attacco e
     * l'inventario.
     */
    private void startCurrentEncounter() {
        this.currentEncounter = this.encounterFactory.createEncounter(this.currentLevel);
        this.enemy = this.currentEncounter.getEnemy();
        this.fight = new Fight(this.hero, this.enemy);
        this.phase = Phase.FIGHTING;
    }

    /**
     * Delega al combattimento l'attacco ordinario e aggiorna l'eventuale esito. Opera soltanto
     * durante FIGHTING. Il combattimento controlla anche la fase interna e gli utilizzi
     * disponibili.
     */
    public void attack() {
        if (phase != Phase.FIGHTING) {
            return;
        }
        this.fight.attack();
        updateFightOutcome();
    }

    /**
     * Delega al combattimento l'uso dell’abilità speciale e aggiorna l'eventuale esito. Opera
     * soltanto durante FIGHTING. Il combattimento controlla anche la fase interna e gli utilizzi
     * disponibili.
     */
    public void useSpecialAbility() {
        if (this.phase != Phase.FIGHTING) {
            return;
        }
        this.fight.useSpecialAbility();
        updateFightOutcome();
    }

    /**
     * Delega al combattimento la ricezione del colpo annunciato e aggiorna l'eventuale esito.
     * Opera soltanto durante FIGHTING. Il combattimento controlla anche la fase interna e gli
     * utilizzi disponibili.
     */
    public void receiveAttack() {
        if (this.phase != Phase.FIGHTING) {
            return;
        }
        this.fight.receiveAttack();
        updateFightOutcome();
    }

    /**
     * Delega al combattimento l'uso della difesa speciale e aggiorna l'eventuale esito. Opera
     * soltanto durante FIGHTING. Il combattimento controlla anche la fase interna e gli utilizzi
     * disponibili.
     */
    public void useSpecialDefense() {
        if (this.phase != Phase.FIGHTING) {
            return;
        }
        this.fight.useSpecialDefense();
        updateFightOutcome();
    }

    /**
     * Se il combattimento è terminato, registra vittoria e bottino passando a DONATION_PENDING
     * oppure registra la sconfitta passando a RETRY_PENDING. Non modifica la sessione se lo
     * scontro continua.
     */
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

    /**
     * Restituisce l’informazione corrente della sessione.
     *
     * @return fase interna del combattimento corrente
     */
    public TurnPhase getTurnPhase() {
        return this.fight.getTurnPhase();
    }

    /**
     * Dona tutto il bottino soltanto durante DONATION_PENDING. Dopo il quinto incontro
     * conclude la partita. Negli altri casi prepara i manufatti tra cui scegliere e passa a
     * REWARD_PENDING.
     *
     * Nelle altre fasi non ha effetto.
     */
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
     * Avvia il livello successivo soltanto durante BETWEEN_ENCOUNTERS, dopo la scelta della
     * ricompensa. Conserva statistiche e inventario dell'eroe. Nelle altre fasi non ha effetto.
     */
    public void startNextEncounter() {
        if (this.phase != Phase.BETWEEN_ENCOUNTERS) {
            return;
        }
        this.currentLevel++;
        startCurrentEncounter();
    }

    /**
     * Aggiunge il manufatto scelto all'inventario, elimina le opzioni e passa a
     * BETWEEN_ENCOUNTERS. Non produce modifiche se fase o indice non sono validi.
     *
     * @param index indice della ricompensa nella lista delle opzioni
     */
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

    /**
     * Restituisce l’informazione corrente della sessione.
     *
     * @return nuova lista dei nomi delle ricompense disponibili
     */
    public List<String> getRewardNames() {
        List<String> names = new ArrayList<>();
        for (Artifact artifact : this.rewardOptions) {
            names.add(artifact.getName());
        }
        return names;
    }

    /**
     * Restituisce l’informazione corrente della sessione.
     *
     * @return nuova lista dei nomi dei manufatti posseduti
     */
    public List<String> getInventoryNames() {
        return this.hero.getInventory().getItemNames();
    }

    /**
     * Usa un manufatto durante il combattimento delegando a Fight, oppure tra incontri senza
     * risposta nemica. Nelle altre fasi non lo usa. Un utilizzo riuscito in combattimento
     * consuma l'azione dell'eroe.
     *
     * @param index indice del manufatto nell'inventario
     * @return true se l'utilizzo è riuscito,
     *         false altrimenti
     */
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
     * Restituisce la disponibilità dell’azione richiesta.
     *
     * @return true soltanto durante BETWEEN_ENCOUNTERS, dopo la scelta della ricompensa
     */
    public boolean canSave() {
        return this.phase == Phase.BETWEEN_ENCOUNTERS;
    }

    /**
     * Restituisce la disponibilità dell’azione richiesta.
     *
     * @return true se la sessione è FIGHTING e il combattimento consente l’abilità
     */
    public boolean canUseSpecialAbility() {
        return this.phase == Phase.FIGHTING && this.fight.canUseSpecialAbility();
    }

    /**
     * Restituisce la disponibilità dell’azione richiesta.
     *
     * @return true se la sessione è FIGHTING e il combattimento consente la difesa
     */
    public boolean canUseSpecialDefense() {
        return this.phase == Phase.FIGHTING && this.fight.canUseSpecialDefense();
    }

    /**
     * Restituisce l’informazione corrente della sessione.
     *
     * @return nuova lista contenente gli stessi riferimenti ai manufatti posseduti
     */
    public List<Artifact> getInventoryItems() {
        return this.hero.getInventory().copyItems();
    }

    public Phase getPhase() {
        return phase;
    }

    public int getHonor() {
        return this.rewards.getHonor();
    }

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

    public int getHeroAttack() {
        return this.hero.getAttack();
    }
}