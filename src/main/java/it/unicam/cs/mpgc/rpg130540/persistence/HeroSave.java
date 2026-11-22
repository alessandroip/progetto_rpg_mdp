package it.unicam.cs.mpgc.rpg130540.persistence;

import java.util.List;

/**
 * La classe HeroSave contiene tipo, statistiche e inventario dell'eroe salvato.
 * Abilità e difesa fisse vengono ricreate dal costruttore dell'eroe durante il caricamento.
 */
public class HeroSave {
    /**
     * Identificativo dell'eroe: ROBIN_HOOD oppure LITTLE_JOHN.
     */
    public String heroType;
    /**
     * Punti vita attuali al momento del salvataggio.
     */
    public int lifePoints;
    /**
     * Attacco attuale, inclusi i bonus permanenti.
     */
    public int attack;
    /**
     * Manufatti posseduti; una lista vuota è valida, null non è valido.
     */
    public List<ItemSave> inventory;
}