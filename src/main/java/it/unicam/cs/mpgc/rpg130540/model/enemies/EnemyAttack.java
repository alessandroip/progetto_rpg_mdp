package it.unicam.cs.mpgc.rpg130540.model.enemies;

public class EnemyAttack {
    private final String name;
    private final int damage;

    public EnemyAttack(String name, int damage) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Il nome non può essere vuoto");
        }

        if (damage < 0) {
            throw new IllegalArgumentException("Il danno non può essere negativo");
        }

        this.name = name;
        this.damage = damage;
    }

    public String getName() {
        return this.name;
    }

    public int getDamage() {
        return this.damage;
    }
}
