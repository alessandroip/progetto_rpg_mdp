package it.unicam.cs.mpgc.rpg130540.persistence;

/**
 * La classe ItemSave contiene identificativo e potenza di un manufatto salvato.
 * È un contenitore di dati per Jackson, senza comportamento di utilizzo o accesso ai file.
 */
public class ItemSave {
    /**
     * Identificativo del manufatto nel formato di salvataggio.
     */
    public String type;
    /**
     * Potenza salvata: quantità di cura o bonus di attacco.
     */
    public int power;
}