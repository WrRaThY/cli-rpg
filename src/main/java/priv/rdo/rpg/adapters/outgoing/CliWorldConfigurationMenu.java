package priv.rdo.rpg.adapters.outgoing;

import priv.rdo.rpg.adapters.util.io.InputParser;
import priv.rdo.rpg.adapters.util.io.OutputWriter;
import priv.rdo.rpg.adapters.util.menu.CliMenuBase;
import priv.rdo.rpg.ports.outgoing.WorldConfigurationMenu;
import priv.rdo.rpg.ports.outgoing.dto.RealmConfiguration;

import java.util.List;

public class CliWorldConfigurationMenu extends CliMenuBase<RealmConfiguration> implements WorldConfigurationMenu {

    public CliWorldConfigurationMenu(InputParser inputParser, OutputWriter outputWriter) {
        super(inputParser, outputWriter);
    }

    @Override
    public RealmConfiguration chooseConfiguration(String realmQuestion, List<RealmConfiguration> realmConfigs) {
        setMenuItems(realmConfigs);

        printAllOptions(realmQuestion);
        return selectOption();
    }

    @Override
    public void confirmRealmConfiguration(String realmConfirmationMessage) {
        outputWriter.showMessageWithSpace(realmConfirmationMessage);
    }
}
