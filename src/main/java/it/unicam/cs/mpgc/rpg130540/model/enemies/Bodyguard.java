package it.unicam.cs.mpgc.rpg130540.model.enemies;

public class Bodyguard extends Enemy {
    private boolean heavyAttackNext;
    private int receivedHits;

    public Bodyguard() {
        super("Guardia del corpo", 16, 5);
    }

    @Override
    public EnemyAttack prepareAttack() {
        EnemyAttack preparedAttack;

        if (this.heavyAttackNext) {
            preparedAttack = new EnemyAttack(
                    "Colpo di mazza pesante", getAttack() + 2
            );
        } else {
            preparedAttack = new EnemyAttack(
                    "Colpo di mazza", getAttack()
            );
        }

        this.heavyAttackNext = !this.heavyAttackNext;
        return preparedAttack;
    }

    @Override
    public void receiveHit(int incomingDamage) {
        if (incomingDamage < 0) {
            throw new IllegalArgumentException("Il danno non può essere negativo");
        }

        if (incomingDamage == 0) {
            return;
        }

        this.receivedHits++;

        int effectiveDamage = incomingDamage;

        if (this.receivedHits % 2 == 0) {
            effectiveDamage = Math.max(0, incomingDamage - 2);
        }

        takeDamage(effectiveDamage);
    }
}