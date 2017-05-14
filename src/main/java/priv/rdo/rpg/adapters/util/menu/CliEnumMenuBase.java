package priv.rdo.rpg.adapters.util.menu;

import priv.rdo.rpg.adapters.util.io.InputParser;
import priv.rdo.rpg.adapters.util.io.OutputWriter;

import java.util.Arrays;

public class CliEnumMenuBase<ItemType extends Enum> extends CliMenuBase<ItemType> {
    protected CliEnumMenuBase(InputParser inputParser, OutputWriter outputWriter, ItemType[] menuItems) {
        super(inputParser, outputWriter, Arrays.asList(menuItems));
    }
}
