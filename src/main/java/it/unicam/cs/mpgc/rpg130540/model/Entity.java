package it.unicam.cs.mpgc.rpg130540.model;

/**
 * Classe astratta rappresentativa delle caratteristiche e delle operazioni comuni a eroi e nemici.
 */
public abstract class Entity {
    protected String name;
    protected int lifePoints;
    protected int lifePointsMax;
    protected int attack;

    /**
     * Il costruttore inizializza un personaggio con i punti vita al massimo.
     *
     * Inoltre, esegue due controlli: uno sul nome che non può essere
     * vuoto e un altro controllo sui punti vita massimi e sull'attacco che non possono
     * essere non positivi.
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
     * Verifica se il personaggio è ancora in vita.
     *
     * @return true se i punti vita attuali sono maggiori di zero,
     *         false altrimenti
     */
    public boolean isAlive() {
        return this.lifePoints > 0;
    }

    /**
     * Sottrae il danno dai punti vita attuali, fino a un minimo di zero.
     * Un danno pari a zero non modifica i punti vita.
     *
     * E' presente un controllo che rifiuta il danno negativo, perché se inserito
     * aumenterebbe la vita dato che l'operazione è una differenza (punti vita - danno).
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
     * Recupera punti vita fino al massimo consentito. Non permette di riportare
     * in vita un personaggio sconfitto. Una cura zero è ammessa per un personaggio
     * vivo.
     *
     * Sceglie il valore minore fra cura richiesta e PV mancanti. Questo evita
     * anche di sommare prima una quantità molto grande per poi correggerla.
     *
     * Rifiuta cure negative e impedisce la cura di un personaggio sconfitto.
     *
     * @param amount quantità massima di punti vita da recuperare, non negativa
     * @throws IllegalArgumentException se la quantità è negativa
     * @throws IllegalStateException se il personaggio è sconfitto
     */
    public void heal(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("La cura non può essere negativa");
        }
        if (!isAlive()) {
            throw new IllegalStateException("Il personaggio è sconfitto");
        }
        int recoveredPoints = Math.min(amount, this.lifePointsMax - this.lifePoints);
        this.lifePoints += recoveredPoints;
    }

    /**
     * Restituisce i punti vita attuali.
     *
     * @return i punti vita attuali
     */
    public int getLifePoints() {
        return this.lifePoints;
    }

    /**
     * Restituisce attacco corrente, inclusi eventuali potenziamenti
     * permanenti dell'eroe.
     *
     * @return attacco attuale, inclusi eventuali potenziamenti permanenti dell'eroe
     */
    public int getAttack() {
        return this.attack;
    }

    /**
     * Restituisce il nome dell'entità.
     *
     * @return il nome dell'entità
     */
    public String getName() {
        return this.name;
    }

    /**
     * Restituisce i punti vita massimi dell'entità.
     *
     * @return i punti vita massimi dell'entità
     */
    public int getLifePointsMax() {
        return this.lifePointsMax;
    }
}