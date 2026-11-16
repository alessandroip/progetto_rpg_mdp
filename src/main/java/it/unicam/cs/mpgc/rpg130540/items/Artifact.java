package it.unicam.cs.mpgc.rpg130540.items;

import it.unicam.cs.mpgc.rpg130540.model.heroes.Hero;

public interface Artifact {

    String getName();

    boolean use(Hero hero);
}
