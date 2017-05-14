package priv.rdo.rpg.domain.character;

import priv.rdo.rpg.common.util.ToStringBuilder;

import java.io.Serializable;

import static priv.rdo.rpg.common.util.Color.GREEN;
import static priv.rdo.rpg.common.util.Color.RED;
import static priv.rdo.rpg.common.util.Color.YELLOW;
import static priv.rdo.rpg.common.util.IntUtils.randomIntInclusive;
import static priv.rdo.rpg.domain.game.StaticMessages.FIGHT_DAMAGE_VARIATION_MULTIPLIER;

public class Character implements Serializable {
    protected final String name;
    protected final String description;

    //can change over time with exp
    protected int maxHp;
    protected int currentHp;
    protected int damage;
    protected int damageVariation;

    protected boolean isAlive;

    public Character(String name, String description, int maxHp, int damage, int damageVariation) {
        this.name = name;
        this.description = description;

        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.damage = damage;
        this.damageVariation = damageVariation;

        this.isAlive = true;
    }

    /**
     * deals damage to otherCharacter
     *
     * @param otherCharacter - enemy
     * @return damage dealt
     */
    public int attack(Character otherCharacter) {
        return otherCharacter.receiveDamage(calculateDamageToDeal());
    }

    private int receiveDamage(int damage) {
        int damageReceived = calculateDamageReceived(damage);
        currentHp -= damageReceived;
        if (currentHp <= 0) {
            die();
        }

        return damageReceived;
    }

    protected int calculateDamageToDeal() {
        return randomIntInclusive(damage, damage + damageVariation * FIGHT_DAMAGE_VARIATION_MULTIPLIER);
    }

    protected int calculateDamageReceived(int damage) {
        return damage;
    }

    public void die() {
        isAlive = false;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public int getDamage() {
        return damage;
    }

    public int getDamageVariation() {
        return damageVariation;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public boolean isDead() {
        return !isAlive;
    }

    public String currentStatus() {
        return "\n" + getClass().getSimpleName() + " current status is: \n" + toStringWithColors();
    }

    @Override
    public String toString() {
        return toStringCommon().build();
    }

    public String toStringWithColors() {
        return toStringCommon().build(true);
    }

    private ToStringBuilder toStringCommon() {
        return ToStringBuilder.fieldsWithNewlinesAndTabs(this)
                .append("name", name, YELLOW)
                .append("description", description)
                .append("health", currentHp + "/" + maxHp, GREEN)
                .append("damage", damage + "-" + (damage + damageVariation * FIGHT_DAMAGE_VARIATION_MULTIPLIER), RED);
    }
}
