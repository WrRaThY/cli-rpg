package priv.rdo.rpg.ports.outgoing;

import priv.rdo.rpg.ports.outgoing.dto.PlayerConfiguration;
import priv.rdo.rpg.ports.outgoing.dto.QuestionsToPlayer;

public interface PlayerConfigurationMenu {
    /**
     * should return a valid player configuration
     * adapter is responsible for implementing initial validation
     */
    PlayerConfiguration askForPlayerConfig(QuestionsToPlayer questionsToPlayer);

    void showIntroduction(String introduction);

    void greetPlayer(String greetingMessage);
}
