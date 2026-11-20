package it.unicam.cs.mpgc.rpg130540.items;

import it.unicam.cs.mpgc.rpg130540.model.heroes.Hero;

/**
 * Classe WildHealingPlant che rappresenta una pianta curativa consumabile e implementa
 * l'interfaccia Artifact. Recupera punti vita senza superare il massimo e
 * non è utilizzabile su un eroe sconfitto o già a vita piena.
 */
public class WildHealingPlant implements Artifact {
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
    public WildHealingPlant(int healingPoints) {
        if (healingPoints <= 0) {
            throw new IllegalArgumentException("La cura deve essere positiva");
        }
        this.healingPoints = healingPoints;
    }

    @Override
    public String getName() {
        return "Pianta selvatica (+" + this.healingPoints + " PV)";
    }

    /**
     * Cura l'eroe senza superare i punti vita massimi. Non applica effetti se
     * l'eroe è sconfitto o a vita piena.
     * La rimozione del manufatto è gestita dall'inventario.
     *
     * @param hero eroe destinatario, non nullo
     * @return true se la cura è applicata,
     *         false se l’eroe è sconfitto o a vita piena
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