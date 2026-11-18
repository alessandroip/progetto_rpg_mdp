package it.unicam.cs.mpgc.rpg130540.persistence;

/**
 * Contiene l'eroe salvato e la progressione della partita.
 */
public class SaveData {
    private int version;
    private HeroSave hero;
    private int honor;
    private int completedLevel;

    public SaveData() {
    }

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