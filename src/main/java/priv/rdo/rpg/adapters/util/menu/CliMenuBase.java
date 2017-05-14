package priv.rdo.rpg.adapters.util.menu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import priv.rdo.rpg.adapters.util.io.InputParser;
import priv.rdo.rpg.adapters.util.io.OutputWriter;
import priv.rdo.rpg.domain.common.exception.GameException;
import priv.rdo.rpg.ports.outgoing.BaseMenu;

import java.util.List;

public abstract class CliMenuBase<ItemType> implements BaseMenu<ItemType> {
    private static final Logger LOG = LogManager.getLogger(CliMenuBase.class);

    protected final InputParser inputParser;
    protected final OutputWriter outputWriter;
    protected final MenuToStringFormatter<ItemType> menuFormatter;

    //TODO: probably a map would enable a more generic solution (especially for exploration menu!)
    protected List<ItemType> menuItems;

    protected CliMenuBase(InputParser inputParser, OutputWriter outputWriter) {
        this(inputParser, outputWriter, null);
    }

    protected CliMenuBase(InputParser inputParser, OutputWriter outputWriter, List<ItemType> menuItems) {
        this.inputParser = inputParser;
        this.outputWriter = outputWriter;
        this.menuItems = menuItems;
        this.menuFormatter = menuFormatter();
    }

    /**
     * can be overridden to change how menu looks like
     */
    protected MenuToStringFormatter<ItemType> menuFormatter() {
        return new MenuToStringFormatter<>();
    }

    public void setMenuItems(List<ItemType> menuItems) {
        this.menuItems = menuItems;
    }

    @Override
    public void showMessage(String message) {
        outputWriter.showMessage(message);
    }

    public ItemType showMenu() {
        printAllOptions();
        return selectOption();
    }

    public void printAllOptions(String message) {
        outputWriter.showMessageWithSpace(message);
        printAllOptions();
    }

    public void printAllOptions() {
        if (null == menuItems) {
            throw new GameException("Menu badly initialized, please contact the creator. Shutting down...");
        }

        outputWriter.showMessageWithSpace("Please choose one of those options:");
        for (int i = 0; i < menuItems.size(); i++) {
            outputWriter.showMessage(formatMessage(menuItems.get(i), i));
        }
    }

    private String formatMessage(ItemType item, int i) {
        return menuFormatter.format(item, i);
    }

    public ItemType selectOption() {
        LOG.trace("trying to select a menu option...");
        ItemType menuItem = readUserChoice();
        LOG.trace("Option '{}' selected", menuItem);
        return menuItem;
    }

    protected ItemType readUserChoice() {
        return menuItems.get(inputParser.tryReadingMenuChoice(menuItems.size()));
    }


}
