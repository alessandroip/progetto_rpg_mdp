package it.unicam.cs.mpgc.rpg130540.persistence;

/**
 * Raccoglie i dati dell'eroe e la progressione di una partita salvata tra due incontri. La
 * coerenza dei valori è controllata da SaveDataValidator prima della ricostruzione.
 */
public class SaveData {
    /**
     * Versione della struttura del salvataggio.
     */
    private int version;
    /**
     * Contenitore modificabile dei dati dell'eroe salvato.
     */
    private HeroSave hero;
    /**
     * Onore accumulato.
     */
    private int honor;
    /**
     * Numero del livello completato prima del salvataggio.
     */
    private int completedLevel;

    /**
     * Il costruttore senza parametri crea un contenitore vuoto per la deserializzazione Jackson.
     * I dati devono essere validati prima della ricostruzione della partita.
     */
    public SaveData() {
    }

    /**
     * Raccoglie i dati di una partita usando la versione uno del formato. Non esegue la
     * validazione delle regole di gioco.
     *
     * @param hero contenitore dei dati dell'eroe
     * @param honor onore accumulato
     * @param completedLevel livello appena completato
     */
    public SaveData(HeroSave hero, int honor, int completedLevel) {
        this.version = 1;
        this.hero = hero;
        this.honor = honor;
        this.completedLevel = completedLevel;
    }

    public int getVersion() {
        return this.version;
    }

    public HeroSave getHero() {
        return this.hero;
    }

    public int getHonor() {
        return this.honor;
    }

    public int getCompletedLevel() {
        return this.completedLevel;
    }
}