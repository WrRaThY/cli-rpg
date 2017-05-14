package priv.rdo.rpg.domain.menu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import priv.rdo.rpg.domain.common.exception.ShouldNeverHappen;
import priv.rdo.rpg.domain.game.GameManager;
import priv.rdo.rpg.ports.exception.ConfigurationException;
import priv.rdo.rpg.ports.outgoing.GameStateProvider;
import priv.rdo.rpg.ports.outgoing.MainMenu;
import priv.rdo.rpg.ports.outgoing.RealmConfigurationProvider;
import priv.rdo.rpg.ports.outgoing.dto.MainMenuItem;

import static priv.rdo.rpg.common.util.ColorFormatter.boldMagenta;
import static priv.rdo.rpg.ports.outgoing.dto.MainMenuItem.EXIT;

//TODO add tests
public class MainMenuManager {
    private static final Logger LOG = LogManager.getLogger(MainMenuManager.class);

    private final RealmConfigurationProvider configurationProvider;
    private final GameStateProvider gameStateProvider;
    private final AllMenus allMenus;
    private final MainMenu mainMenu;

    public MainMenuManager(RealmConfigurationProvider configurationProvider, AllMenus allMenus, GameStateProvider gameStateProvider) {
        this.allMenus = allMenus;
        this.mainMenu = allMenus.mainMenu();
        this.configurationProvider = configurationProvider;
        this.gameStateProvider = gameStateProvider;
    }

    public void showMenu() throws ConfigurationException {
        LOG.traceEntry();

        MainMenuItem choice;
        do {
            mainMenu.showMessage(boldMagenta("\nWelcome to Main Menu"));
            choice = mainMenu.showMenu();

            switch (choice) {
                case START:
                    GameManager.newGame(gameStateProvider, allMenus, configurationProvider.load());
                    break;
                case LOAD:
                    GameManager.loadGame(gameStateProvider, allMenus);
                    break;
                case EXIT:
                    mainMenu.showMessage("Come back soon! :)");
                    break;
                default:
                    throw new ShouldNeverHappen();
            }
        } while (EXIT != choice);


        LOG.traceExit();
    }


}
