package it.unicam.cs.mpgc.rpg130540.items;

/**
 * Classe Blowpipe, consumabile, che aumenta permanentemente l'attacco dell'eroe.
 * Non è un'arma equipaggiabile: dopo l'uso riuscito viene rimossa dall'inventario.
 */
public class Blowpipe extends AttackBoostArtifact {

    /**
     * Il costruttore crea la cerbottana.
     *
     * @param bonus incremento permanente di attacco, maggiore di zero
     * @throws IllegalArgumentException se il bonus non è positivo
     */
    public Blowpipe(int bonus) {
        super(bonus);
    }

    @Override
    public String getName() {
        return "Cerbottana (+" + getBonus() + " attacco)";
    }
}