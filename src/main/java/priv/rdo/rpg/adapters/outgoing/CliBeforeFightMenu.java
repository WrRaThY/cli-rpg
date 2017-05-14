package priv.rdo.rpg.adapters.outgoing;

import priv.rdo.rpg.adapters.util.io.InputParser;
import priv.rdo.rpg.adapters.util.io.OutputWriter;
import priv.rdo.rpg.adapters.util.menu.CliEnumMenuBase;
import priv.rdo.rpg.ports.outgoing.BeforeFightMenu;
import priv.rdo.rpg.ports.outgoing.dto.BeforeFightMenuItem;

public class CliBeforeFightMenu extends CliEnumMenuBase<BeforeFightMenuItem> implements BeforeFightMenu {
    public CliBeforeFightMenu(InputParser inputParser, OutputWriter outputWriter) {
        super(inputParser, outputWriter, BeforeFightMenuItem.values());
    }
}
