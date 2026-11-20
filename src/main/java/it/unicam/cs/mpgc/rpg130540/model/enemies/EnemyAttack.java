package it.unicam.cs.mpgc.rpg130540.model.enemies;

/**
 * Classe EnemyAttack che descrive il colpo annunciato dal nemico.
 */
public class EnemyAttack {
    private final String name;
    private final int damage;

    /**
     * Il costruttore crea la descrizione di un attacco annunciato.
     *
     * @param name nome del colpo, non nullo e non vuoto
     * @param damage danno prima della difesa dell'eroe, non negativo
     * @throws IllegalArgumentException se il nome non è valido o il danno è negativo
     */
    public EnemyAttack(String name, int damage) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Il nome non può essere vuoto");
        }
        if (damage < 0) {
            throw new IllegalArgumentException("Il danno non può essere negativo");
        }
        this.name = name;
        this.damage = damage;
    }

    /**
     * Restituisce il nome del colpo.
     *
     * @return nome del colpo annunciato
     */
    public String getName() {
        return this.name;
    }

    /**
     * Restituisce il danno del colpo prima della difesa.
     *
     * @return danno annunciato, maggiore o uguale a zero
     */
    public int getDamage() {
        return this.damage;
    }
}
