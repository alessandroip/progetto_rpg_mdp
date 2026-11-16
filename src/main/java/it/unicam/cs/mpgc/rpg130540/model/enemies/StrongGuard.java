package it.unicam.cs.mpgc.rpg130540.model.enemies;

public class StrongGuard extends Enemy {
    private boolean heavyAttackNext;

    public StrongGuard() {
        super("Guardia forte", 12, 4);
    }

    @Override
    public EnemyAttack prepareAttack() {
        EnemyAttack preparedAttack;

        if (this.heavyAttackNext) {
            preparedAttack = new EnemyAttack("Affondo pesante", getAttack() + 2);
        } else {
            preparedAttack = new EnemyAttack("Colpo di spada", getAttack());
        }
        this.heavyAttackNext = !this.heavyAttackNext;
        return preparedAttack;
    }

    @Override
    public void receiveHit(int incomingDamage) {
        if (incomingDamage < 0) {
            throw new IllegalArgumentException("Il danno non può essere negativo");
        }
        int effectiveDamage = Math.max(0, incomingDamage - 1);
        takeDamage(effectiveDamage);
    }


}
