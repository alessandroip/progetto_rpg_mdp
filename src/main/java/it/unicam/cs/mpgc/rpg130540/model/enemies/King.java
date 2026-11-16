package it.unicam.cs.mpgc.rpg130540.model.enemies;

public class King extends Enemy {
    private int preparedAttacks;
    private int shieldUses = 2;

    public King() {
        super("Re", 20, 7);
    }

    @Override
    public EnemyAttack prepareAttack() {
        this.preparedAttacks++;

        if (this.preparedAttacks % 3 == 0) {
            return new EnemyAttack(
                    "Furia del re", getAttack() + 3
            );
        }

        return new EnemyAttack("Colpo della corona", getAttack());
    }

    @Override
    public void receiveHit(int incomingDamage) {
        if (incomingDamage < 0) {
            throw new IllegalArgumentException("Il danno non può essere negativo");
        }

        if (incomingDamage == 0) {
            return;
        }

        int effectiveDamage = incomingDamage;

        if (this.shieldUses > 0) {
            effectiveDamage = Math.max(0, incomingDamage - 3);
            this.shieldUses--;
        }

        takeDamage(effectiveDamage);
    }
}
