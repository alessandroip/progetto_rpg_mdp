package it.unicam.cs.mpgc.rpg130540.items;

import it.unicam.cs.mpgc.rpg130540.model.heroes.Hero;

/**
 * Manufatto consumabile che aumenta permanentemente l'attacco dell'eroe.
 * Le sottoclassi definiscono soltanto il nome mostrato al giocatore.
 */
public abstract class AttackBoostArtifact implements Artifact {
    /**
     * Incremento permanente dell'attacco.
     */
    private final int bonus;

    /**
     * Crea il manufatto con il bonus di attacco indicato.
     *
     * @param bonus incremento permanente di attacco, maggiore di zero
     * @throws IllegalArgumentException se il bonus non è positivo
     */
    protected AttackBoostArtifact(int bonus) {
        if (bonus <= 0) {
            throw new IllegalArgumentException("Il bonus deve essere positivo");
        }
        this.bonus = bonus;
    }

    /**
     * Aumenta permanentemente l'attacco dell'eroe vivo. La rimozione del
     * manufatto è gestita dall'inventario.
     *
     * @param hero eroe destinatario, non nullo
     * @return true se il bonus è applicato,
     *         false se l'eroe è sconfitto
     * @throws ArithmeticException se il bonus supera il limite di int
     */
    @Override
    public boolean use(Hero hero) {
        if (!hero.isAlive()) {
            return false;
        }
        hero.increaseAttack(this.bonus);
        return true;
    }

    /**
     * Restituisce la potenza configurata, usata anche dal salvataggio.
     *
     * @return incremento permanente di attacco
     */
    public int getBonus() {
        return this.bonus;
    }
}