package priv.rdo.rpg.adapters.outgoing;

import priv.rdo.rpg.adapters.util.io.InputParser;
import priv.rdo.rpg.adapters.util.io.OutputWriter;
import priv.rdo.rpg.adapters.util.menu.CliEnumMenuBase;
import priv.rdo.rpg.ports.outgoing.MainMenu;
import priv.rdo.rpg.ports.outgoing.dto.MainMenuItem;

public class CliMainMenu extends CliEnumMenuBase<MainMenuItem> implements MainMenu {

    public CliMainMenu(InputParser inputParser, OutputWriter outputWriter) {
        super(inputParser, outputWriter, MainMenuItem.values());
    }

    @Override
    public MainMenuItem showMenu() {
        printAllOptions();
        return selectOption();
    }
}
