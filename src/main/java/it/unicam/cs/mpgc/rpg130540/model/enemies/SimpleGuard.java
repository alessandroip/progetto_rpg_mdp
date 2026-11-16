package it.unicam.cs.mpgc.rpg130540.model.enemies;

public class SimpleGuard extends Enemy {

    public SimpleGuard() {
        super("Guardia semplice", 10, 4);
    }

    @Override
    public EnemyAttack prepareAttack() {
        return new EnemyAttack("Fendente della guardia", getAttack());
    }
}
