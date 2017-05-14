package priv.rdo.rpg.ports.outgoing.dto;

public class QuestionsToPlayer {
    private final String nameQuestion;
    private final String descQuestion;
    private final String bonusStatsDescription;
    private final String bonusStatsBonusHpQuestion;
    private final String bonusStatsBonusDanageQuestion;
    private final String bonusStatsBonusDanageVariationQuestion;
    private final int maxBonusPoints;

    private QuestionsToPlayer(String nameQuestion, String descQuestion, String bonusStatsDescription, String bonusStatsBonusHpQuestion,
                              String bonusStatsBonusDanageQuestion, String bonusStatsBonusDanageVariationQuestion, int maxBonusPoints) {
        this.nameQuestion = nameQuestion;
        this.descQuestion = descQuestion;
        this.bonusStatsDescription = bonusStatsDescription;
        this.bonusStatsBonusHpQuestion = bonusStatsBonusHpQuestion;
        this.bonusStatsBonusDanageQuestion = bonusStatsBonusDanageQuestion;
        this.bonusStatsBonusDanageVariationQuestion = bonusStatsBonusDanageVariationQuestion;
        this.maxBonusPoints = maxBonusPoints;
    }

    public String getNameQuestion() {
        return nameQuestion;
    }

    public String getDescQuestion() {
        return descQuestion;
    }

    public String getBonusStatsDescription() {
        return bonusStatsDescription;
    }

    public String getBonusStatsBonusHpQuestion() {
        return bonusStatsBonusHpQuestion;
    }

    public String getBonusStatsBonusDanageQuestion() {
        return bonusStatsBonusDanageQuestion;
    }

    public String getBonusStatsBonusDanageVariationQuestion() {
        return bonusStatsBonusDanageVariationQuestion;
    }

    public int getMaxBonusPoints() {
        return maxBonusPoints;
    }

    public static QuestionsToPlayerBuilder builder() {
        return new QuestionsToPlayerBuilder();
    }

    public static final class QuestionsToPlayerBuilder {
        private String nameQuestion;
        private String descQuestion;
        private String bonusStatsDescription;
        private String bonusStatsBonusHpQuestion;
        private String bonusStatsBonusDanageQuestion;
        private String bonusStatsBonusDanageVariationQuestion;
        private int maxBonusPoints;

        private QuestionsToPlayerBuilder() {
        }

        public QuestionsToPlayerBuilder withNameQuestion(String nameQuestion) {
            this.nameQuestion = nameQuestion;
            return this;
        }

        public QuestionsToPlayerBuilder withDescQuestion(String descQuestion) {
            this.descQuestion = descQuestion;
            return this;
        }

        public QuestionsToPlayerBuilder withBonusStatsDescription(String bonusStatsDescription) {
            this.bonusStatsDescription = bonusStatsDescription;
            return this;
        }

        public QuestionsToPlayerBuilder withBonusStatsBonusHpQuestion(String bonusStatsBonusHpQuestion) {
            this.bonusStatsBonusHpQuestion = bonusStatsBonusHpQuestion;
            return this;
        }

        public QuestionsToPlayerBuilder withBonusStatsBonusDamageQuestion(String bonusStatsBonusDanageQuestion) {
            this.bonusStatsBonusDanageQuestion = bonusStatsBonusDanageQuestion;
            return this;
        }

        public QuestionsToPlayerBuilder withBonusStatsBonusDamageVariationQuestion(String bonusStatsBonusDanageVariationQuestion) {
            this.bonusStatsBonusDanageVariationQuestion = bonusStatsBonusDanageVariationQuestion;
            return this;
        }

        public QuestionsToPlayerBuilder withMaxBonusPoints(int maxBonusPoints) {
            this.maxBonusPoints = maxBonusPoints;
            return this;
        }

        public QuestionsToPlayer build() {
            return new QuestionsToPlayer(nameQuestion, descQuestion, bonusStatsDescription, bonusStatsBonusHpQuestion,
                    bonusStatsBonusDanageQuestion, bonusStatsBonusDanageVariationQuestion, maxBonusPoints);
        }
    }
}
