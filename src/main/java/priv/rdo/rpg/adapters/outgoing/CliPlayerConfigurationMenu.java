package priv.rdo.rpg.adapters.outgoing;

import priv.rdo.rpg.adapters.util.io.InputParser;
import priv.rdo.rpg.adapters.util.io.OutputWriter;
import priv.rdo.rpg.adapters.util.menu.CliMenuBase;
import priv.rdo.rpg.domain.common.exception.PlayerValidationException;
import priv.rdo.rpg.ports.outgoing.PlayerConfigurationMenu;
import priv.rdo.rpg.ports.outgoing.dto.PlayerConfiguration;
import priv.rdo.rpg.ports.outgoing.dto.QuestionsToPlayer;

public class CliPlayerConfigurationMenu extends CliMenuBase<String> implements PlayerConfigurationMenu {

    public CliPlayerConfigurationMenu(InputParser inputParser, OutputWriter outputWriter) {
        super(inputParser, outputWriter);
    }

    @Override
    public PlayerConfiguration askForPlayerConfig(QuestionsToPlayer questions) {
        PlayerConfiguration playerConfiguration = null;

        PlayerConfiguration.PlayerConfigurationBuilder playerConfigurationBuilder = PlayerConfiguration.builder(questions.getMaxBonusPoints());
        playerConfigurationBuilder.withName(inputParser.readUserInputAsString(questions.getNameQuestion()));
        playerConfigurationBuilder.withDesc(inputParser.readUserInputAsString(questions.getDescQuestion()));

        do {
            try {
                showMessage(questions.getBonusStatsDescription());
                playerConfigurationBuilder.withHpBonus(inputParser.tryReadingInputAsInt(questions.getBonusStatsBonusHpQuestion()));
                playerConfigurationBuilder.withDamageBonus(inputParser.tryReadingInputAsInt(questions.getBonusStatsBonusDanageQuestion()));
                playerConfigurationBuilder.withDamageVariationBonus(inputParser.tryReadingInputAsInt(questions.getBonusStatsBonusDanageVariationQuestion()));

                playerConfiguration = playerConfigurationBuilder.build();
            } catch (PlayerValidationException e) {
                showMessage(e.getMessage());
            }
        } while (playerConfiguration == null);

        return playerConfiguration;
    }

    @Override
    public void showIntroduction(String introduction) {
        outputWriter.showMessageWithSpace(introduction);
    }

    @Override
    public void greetPlayer(String greetingMessage) {
        outputWriter.showMessageWithSpace(greetingMessage);
    }
}
