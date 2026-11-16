package it.unicam.cs.mpgc.rpg130540.model;

/**
 * Classe astratta rappresentativa delle entità e dei personaggi presenti all'interno del gioco.
 */
public abstract class Entity {
    protected String name;
    protected int lifePoints;
    protected int lifePointsMax;
    protected int attack;

    /**
     * Il costruttore della classe Entity inizializza un personaggio con i punti vita al massimo.
     *
     * Inoltre, esegue due controlli: uno sul nome che non può essere
     * vuoto e un altro controllo sui punti vita massimi e sull'attacco che non possono essere non positivi.
     *
     * @param name          nome del personaggio
     * @param lifePointsMax punti vita massimi, maggiori di zero
     * @param attack        valore dell'attacco base, maggiore di zero
     * @throws IllegalArgumentException se il nome è nullo o privo di
     *                                  caratteri visibili, oppure se vita massima o attacco
     *                                  sono minori o uguali a zero
     */
    public Entity(String name, int lifePointsMax, int attack) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Il nome non può essere vuoto");
        }
        if (lifePointsMax <= 0 || attack <= 0) {
            throw new IllegalArgumentException("Punti vita massimi e attacco devono essere positivi");
        }
        this.name = name;
        this.lifePointsMax = lifePointsMax;
        this.lifePoints = lifePointsMax;
        this.attack = attack;
    }

    /**
     * @return true se il personaggio ha almeno un punto vita,
     *         false se ha zero punti vita
     */
    public boolean isAlive() {
        return this.lifePoints > 0;
    }

    /**
     * Metodo che sottrae il danno dai punti vita attuali, fino a un minimo di zero.
     *
     * E' presente un controllo che rifiuta il danno negativo, perché se inserito aumenterebbe la vita
     * dato che l'operazione è una differenza (punti vita - danno).
     *
     * @param damage quantità di danno ricevuta, maggiore o uguale a zero
     * @throws IllegalArgumentException se il danno è negativo
     */
    public void takeDamage(int damage) {
        if (damage < 0) {
            throw new IllegalArgumentException("Il danno non può essere negativo!");
        }
        this.lifePoints = Math.max(0, this.lifePoints - damage);
    }

    /**
     * Metodo getter che permette di leggere i punti vita dell'entità.
     *
     * @return i punti vita attuali del personaggio
     */
    public int getLifePoints() {
        return this.lifePoints;
    }

    /**
     * Metodo getter che permette di leggere il valore dell'attacco dell'entità.
     *
     * @return il valore dell'attacco base del personaggio
     */
    public int getAttack() {
        return this.attack;
    }

    public String getName() {
        return this.name;
    }

    public int getLifePointsMax() {
        return this.lifePointsMax;
    }
}