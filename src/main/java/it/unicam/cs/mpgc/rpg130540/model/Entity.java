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
     * Il costruttore della classe Entity esegue due controlli: uno sul nome che non può essere
     * vuoto e un altro controllo sui punti vita massimi e sull'attacco che non possono essere negativi.
     *
     * @param name
     * @param lifePointsMax
     * @param attack
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
     * Metodo che fa ridurre i punti vita all'entità che riceve un attacco.
     *
     * E' presente un controllo che rifiuta il danno non strettamente positivo, perché se inserito aumenterebbe la vita
     * dato che l'operazione è una differenza (punti vita - danno).
     *
     * @param damage
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
     * @return
     */
    public int getLifePoints() {
        return this.lifePoints;
    }

    /**
     * Metodo getter che permette di leggere il valore dell'attacco dell'entità.
     *
     * @return
     */
    public int getAttack() {
        return this.attack;
    }
}