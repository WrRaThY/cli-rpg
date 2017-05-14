package priv.rdo.rpg.adapters.outgoing;

import priv.rdo.rpg.adapters.util.io.InputParser;
import priv.rdo.rpg.adapters.util.io.OutputWriter;
import priv.rdo.rpg.adapters.util.menu.CliEnumMenuBase;
import priv.rdo.rpg.ports.outgoing.FightMenu;
import priv.rdo.rpg.ports.outgoing.dto.FightMenuItem;

public class CliFightMenu extends CliEnumMenuBase<FightMenuItem> implements FightMenu {
    public CliFightMenu(InputParser inputParser, OutputWriter outputWriter) {
        super(inputParser, outputWriter, FightMenuItem.values());
    }
}
