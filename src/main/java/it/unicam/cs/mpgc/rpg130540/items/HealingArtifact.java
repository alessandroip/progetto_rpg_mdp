package it.unicam.cs.mpgc.rpg130540.items;

import it.unicam.cs.mpgc.rpg130540.model.heroes.Hero;

/**
 * Classe astratta HealingArtifact che crea un manufatto consumabile che recupera
 * punti vita senza superare il massimo.
 * Non è utilizzabile su un eroe sconfitto o già a vita piena. Le sottoclassi
 * definiscono soltanto il nome mostrato al giocatore.
 */
public abstract class HealingArtifact implements Artifact {
    /**
     * Quantità massima di cura applicabile.
     */
    private final int healingPoints;


    /**
     * Il costruttore crea il manufatto con la quantità di cura indicata.
     *
     * @param healingPoints punti vita massimi recuperabili, maggiore di zero
     * @throws IllegalArgumentException se la cura non è positiva
     */
    protected HealingArtifact(int healingPoints) {
        if (healingPoints <= 0) {
            throw new IllegalArgumentException("La cura deve essere positiva");
        }
        this.healingPoints = healingPoints;
    }

    /**
     * Cura l'eroe senza superare i punti vita massimi. La rimozione del
     * manufatto è gestita dall'inventario.
     *
     * @param hero eroe destinatario, non nullo
     * @return true se la cura è applicata,
     *         false se l'eroe è sconfitto o a vita piena
     */
    @Override
    public boolean use(Hero hero) {
        if (!hero.isAlive() || hero.getLifePoints() == hero.getLifePointsMax()) {
            return false;
        }
        hero.heal(this.healingPoints);
        return true;
    }

    /**
     * Restituisce la potenza configurata, usata anche dal salvataggio.
     *
     * @return quantità massima di cura
     */
    public int getHealingPoints() {
        return this.healingPoints;
    }
}