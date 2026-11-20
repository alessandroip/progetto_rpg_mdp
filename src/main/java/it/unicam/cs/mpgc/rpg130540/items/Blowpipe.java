package it.unicam.cs.mpgc.rpg130540.items;

import it.unicam.cs.mpgc.rpg130540.model.heroes.Hero;

/**
 * Classe Blowpipe che rappresenta una cerbottana consumabile che aumenta
 * permanentemente l'attacco. Non costituisce un'arma equipaggiabile:
 * dopo l'uso riuscito viene rimossa dall'inventario.
 */
public class Blowpipe implements Artifact {
    /**
     * Incremento permanente dell'attacco.
     */
    private final int bonus;

    /**
     * Il costruttore crea il manufatto con il bonus di attacco indicato.
     *
     * @param bonus incremento permanente di attacco, maggiore di zero
     * @throws IllegalArgumentException se il bonus non è positivo
     */
    public Blowpipe(int bonus) {
        if (bonus <= 0) {
            throw new IllegalArgumentException("Il bonus deve essere positivo");
        }
        this.bonus = bonus;
    }

    @Override
    public String getName() {
        return "Cerbottana (+" + this.bonus + "attacco)";
    }

    /**
     * Aumenta permanentemente l'attacco dell'eroe vivo. Non applica effetti
     * se l'eroe è sconfitto. La rimozione del manufatto è gestita dall'inventario.
     *
     * @param hero eroe destinatario, non nullo
     * @return true se il bonus è applicato,
     *         false se l’eroe è sconfitto
     * @throws ArithmeticException se il bonus provoca il superamento del limite di int
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