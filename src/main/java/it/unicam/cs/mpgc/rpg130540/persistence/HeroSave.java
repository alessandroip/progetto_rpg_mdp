package it.unicam.cs.mpgc.rpg130540.persistence;

import java.util.List;

/**
 * Contiene le statistiche e l'inventario dell'eroe salvato.
 */
public class HeroSave {
    public String heroType;
    public int lifePoints;
    public int attack;
    public List<ItemSave> inventory;
}
