package it.unicam.cs.mpgc.rpg130540.model.enemies;

public class Mage extends Enemy {
    private boolean powerfulSpellNext;
    private boolean barrierUsed;

    public Mage() {
        super("Mago", 14, 6);
    }

    @Override
    public EnemyAttack prepareAttack() {
        EnemyAttack preparedAttack;

        if (this.powerfulSpellNext) {
            preparedAttack = new EnemyAttack(
                    "Esplosione arcana", getAttack() + 3
            );
        } else {
            preparedAttack = new EnemyAttack(
                    "Scintilla arcana", getAttack() / 2
            );
        }

        this.powerfulSpellNext = !this.powerfulSpellNext;
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
        if (!this.barrierUsed) {
            this.barrierUsed = true;
            return;
        }
        takeDamage(incomingDamage);
    }
}
