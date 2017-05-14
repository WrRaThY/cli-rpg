package priv.rdo.rpg.ports.outgoing.dto;

import priv.rdo.rpg.common.util.StringUtils;
import priv.rdo.rpg.domain.common.exception.PlayerValidationException;

import static priv.rdo.rpg.common.util.ColorFormatter.bold;
import static priv.rdo.rpg.common.util.ColorFormatter.red;

public class PlayerConfiguration {
    private final String name;
    private final String desc;
    private final int hpBonus;
    private final int damageBonus;
    private final int damageVariationBonus;

    private PlayerConfiguration(String name, String desc, int hpBonus, int damageBonus, int damageVariationBonus) {
        this.name = name;
        this.desc = desc;
        this.hpBonus = hpBonus;
        this.damageBonus = damageBonus;
        this.damageVariationBonus = damageVariationBonus;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public int getHpBonus() {
        return hpBonus;
    }

    public int getDamageBonus() {
        return damageBonus;
    }

    public int getDamageVariationBonus() {
        return damageVariationBonus;
    }

    public static PlayerConfigurationBuilder builder(int maxBonusStats) {
        return new PlayerConfigurationBuilder(maxBonusStats);
    }

    public static final class PlayerConfigurationBuilder {
        private final int maxBonusStats;

        private String name;
        private String desc;
        private int hpBonus;
        private int damageBonus;
        private int damageVariationBonus;

        private PlayerConfigurationBuilder(int maxBonusStats) {
            this.maxBonusStats = maxBonusStats;
        }

        public PlayerConfigurationBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public PlayerConfigurationBuilder withDesc(String desc) {
            this.desc = desc;
            return this;
        }

        public PlayerConfigurationBuilder withHpBonus(int hpBonus) {
            this.hpBonus = hpBonus;
            return this;
        }

        public PlayerConfigurationBuilder withDamageBonus(int damageBonus) {
            this.damageBonus = damageBonus;
            return this;
        }

        public PlayerConfigurationBuilder withDamageVariationBonus(int damageVariationBonus) {
            this.damageVariationBonus = damageVariationBonus;
            return this;
        }

        public PlayerConfiguration build() throws PlayerValidationException {
            validate();
            return new PlayerConfiguration(name, desc, hpBonus, damageBonus, damageVariationBonus);
        }

        private void validate() throws PlayerValidationException {
            if (StringUtils.isBlank(name)) {
                throw new PlayerValidationException("Name cannot be empty");
            }

            defaultEmptyDescIfNotSet();

            if (statBonusesOutsideLimit(maxBonusStats)) {
                throw new PlayerValidationException(bold(red("Too many bonus stats, the limit is " + maxBonusStats)));
            }
        }

        private void defaultEmptyDescIfNotSet() {
            if (null == desc) {
                desc = "";
            }
        }

        private boolean statBonusesOutsideLimit(int maxBonusStats) {
            int statSum = hpBonus + damageBonus + damageVariationBonus;
            return statSum > maxBonusStats || statSum < 0;
        }
    }
}
