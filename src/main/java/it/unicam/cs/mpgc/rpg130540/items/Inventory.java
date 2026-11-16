package it.unicam.cs.mpgc.rpg130540.items;

import it.unicam.cs.mpgc.rpg130540.model.heroes.Hero;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private final List<Artifact> items = new ArrayList<>();

    public void add(Artifact artifact) {
        if (artifact == null) {
            throw new IllegalArgumentException("Il manufatto è obbligatorio");
        }
        this.items.add(artifact);
    }

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

    public List<String> getItemNames() {
        List<String> names = new ArrayList<>();

        for (Artifact artifact : this.items) {
            names.add(artifact.getName());
        }
        return names;
    }
}
