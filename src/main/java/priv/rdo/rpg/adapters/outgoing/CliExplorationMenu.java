package priv.rdo.rpg.adapters.outgoing;

import priv.rdo.rpg.adapters.util.io.InputParser;
import priv.rdo.rpg.adapters.util.io.OutputWriter;
import priv.rdo.rpg.adapters.util.menu.CliEnumMenuBase;
import priv.rdo.rpg.adapters.util.menu.ExplorationMenuToStringFormatter;
import priv.rdo.rpg.adapters.util.menu.MenuToStringFormatter;
import priv.rdo.rpg.ports.outgoing.ExplorationMenu;
import priv.rdo.rpg.ports.outgoing.dto.ExplorationMenuItem;

public class CliExplorationMenu extends CliEnumMenuBase<ExplorationMenuItem> implements ExplorationMenu {
    public CliExplorationMenu(InputParser inputParser, OutputWriter outputWriter) {
        super(inputParser, outputWriter, ExplorationMenuItem.values());
    }

    @Override
    public void showMap(String map) {
        outputWriter.showMessageWithSpace(map);
    }

    @Override
    public void showStatistics(String statistics) {
        outputWriter.showMessageWithSpace(statistics);
    }

    @Override
    protected MenuToStringFormatter<ExplorationMenuItem> menuFormatter() {
        return new ExplorationMenuToStringFormatter();
    }

    @Override
    protected ExplorationMenuItem readUserChoice() {
        ExplorationMenuItem choice = ExplorationMenuItem.fromString(inputParser.readUserInputAsString());
        if (null == choice) {
            showMessage("You have chosen a wrong option, please try again");
            return ExplorationMenuItem.COMMANDS;
        }

        return choice;
    }

}
