package it.unicam.cs.mpgc.rpg130540.items;

import it.unicam.cs.mpgc.rpg130540.model.heroes.Hero;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe InventoryManager che conserva i manufatti posseduti da un singolo
 * eroe e ne gestisce aggiunta e utilizzo. Non rappresenta un catalogo dei tipi
 * disponibili nel gioco.
 */
public class InventoryManager {
    /**
     * Manufatti posseduti, mantenuti nel loro ordine.
     */
    private final List<Artifact> items = new ArrayList<>();

    /**
     * Aggiunge un manufatto all'inventario.
     *
     * @param artifact manufatto da aggiungere, non nullo
     * @throws IllegalArgumentException se il manufatto è nullo
     */
    public void add(Artifact artifact) {
        if (artifact == null) {
            throw new IllegalArgumentException("Il manufatto è obbligatorio");
        }
        this.items.add(artifact);
    }

    /**
     * Tenta di utilizzare il manufatto all'indice indicato e lo rimuove soltanto
     * se l'effetto viene applicato. Un indice non valido non produce modifiche.
     *
     * @param index posizione del manufatto nella lista
     * @param hero eroe destinatario, non nullo
     * @return true se il manufatto è stato utilizzato e rimosso,
     *         false se l'indice non è valido o l'utilizzo non riesce
     */
    public boolean use(int index, Hero hero) {
        if (index < 0 || index >= this.items.size()) {
            return false;
        }
        Artifact artifact = this.items.get(index);
        if (!artifact.use(hero)) {
            return false;
        }
        this.items.remove(index);
        return true;
    }

    /**
     * Prepara una nuova lista con i nomi dei manufatti nell'ordine dell'inventario.
     *
     * @return lista dei nomi; modificarla non modifica l’inventario
     */
    public List<String> getItemNames() {
        List<String> names = new ArrayList<>();
        for (Artifact artifact : this.items) {
            names.add(artifact.getName());
        }
        return names;
    }

    /**
     * Copia il contenitore dell'inventario senza duplicare i manufatti.
     * Gli elementi della nuova lista sono gli stessi riferimenti presenti nell'inventario.
     *
     * @return nuova lista dei manufatti posseduti
     */
    public List<Artifact> copyItems() {
        return new ArrayList<>(this.items);
    }
}