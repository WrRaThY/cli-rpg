package priv.rdo.rpg.common.context;

import priv.rdo.rpg.adapters.outgoing.CliBeforeFightMenu;
import priv.rdo.rpg.adapters.outgoing.CliExplorationMenu;
import priv.rdo.rpg.adapters.outgoing.CliFightMenu;
import priv.rdo.rpg.adapters.outgoing.CliMainMenu;
import priv.rdo.rpg.adapters.outgoing.CliPlayerConfigurationMenu;
import priv.rdo.rpg.adapters.outgoing.CliWorldConfigurationMenu;
import priv.rdo.rpg.adapters.outgoing.SerializedGameStateProvider;
import priv.rdo.rpg.adapters.outgoing.SerializedRealmConfigurationProvider;
import priv.rdo.rpg.adapters.util.io.InputParser;
import priv.rdo.rpg.adapters.util.io.OutputWriter;
import priv.rdo.rpg.domain.common.exception.DIException;
import priv.rdo.rpg.domain.menu.AllMenus;
import priv.rdo.rpg.domain.menu.MainMenuManager;
import priv.rdo.rpg.ports.outgoing.BeforeFightMenu;
import priv.rdo.rpg.ports.outgoing.ExplorationMenu;
import priv.rdo.rpg.ports.outgoing.FightMenu;
import priv.rdo.rpg.ports.outgoing.GameStateProvider;
import priv.rdo.rpg.ports.outgoing.MainMenu;
import priv.rdo.rpg.ports.outgoing.PlayerConfigurationMenu;
import priv.rdo.rpg.ports.outgoing.RealmConfigurationProvider;
import priv.rdo.rpg.ports.outgoing.WorldConfigurationMenu;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO:
 * this should be done by some DI framework... but guess what... I can't use them in here!
 * so I just quickly wrote a very simple one, just because it's useful
 */
public class DIContext {
    private static final Map<Class, Object> context = new HashMap<>();

    static {
        consoleIo();
        configurationProviders();
        loadSaveGame();
        menus();
        managers();
    }

    private DIContext() {
    }

    private static void consoleIo() {
        OutputWriter outputWriter = new OutputWriter(System.out);
        extendContext(OutputWriter.class, outputWriter);

        InputParser inputParser = new InputParser(outputWriter, System.in);
        extendContext(InputParser.class, inputParser);
    }

    private static void configurationProviders() {
        RealmConfigurationProvider realmConfigurationProvider = new SerializedRealmConfigurationProvider();
        extendContext(RealmConfigurationProvider.class, realmConfigurationProvider);
    }

    private static void loadSaveGame() {
        GameStateProvider gameStateProvider = new SerializedGameStateProvider();
        extendContext(GameStateProvider.class, gameStateProvider);
    }

    private static void menus() {
        OutputWriter outputWriter = getBean(OutputWriter.class);
        InputParser inputParser = getBean(InputParser.class);


        PlayerConfigurationMenu playerConfigurationMenu = new CliPlayerConfigurationMenu(inputParser, outputWriter);
        extendContext(PlayerConfigurationMenu.class, playerConfigurationMenu);

        WorldConfigurationMenu worldConfigurationMenu = new CliWorldConfigurationMenu(inputParser, outputWriter);
        extendContext(WorldConfigurationMenu.class, worldConfigurationMenu);

        MainMenu mainMenu = new CliMainMenu(inputParser, outputWriter);
        extendContext(MainMenu.class, mainMenu);

        ExplorationMenu explorationMenu = new CliExplorationMenu(inputParser, outputWriter);
        extendContext(ExplorationMenu.class, explorationMenu);

        FightMenu fightMenu = new CliFightMenu(inputParser, outputWriter);
        extendContext(FightMenu.class, fightMenu);

        BeforeFightMenu beforeFightMenu = new CliBeforeFightMenu(inputParser, outputWriter);
        extendContext(BeforeFightMenu.class, beforeFightMenu);

        AllMenus allMenus = new AllMenus(mainMenu, playerConfigurationMenu, worldConfigurationMenu, explorationMenu, beforeFightMenu, fightMenu);
        extendContext(AllMenus.class, allMenus);
    }

    private static void managers() {
        AllMenus allMenus = getBean(AllMenus.class);
        GameStateProvider gameStateProvider = getBean(GameStateProvider.class);
        RealmConfigurationProvider realmConfigurationProvider = getBean(RealmConfigurationProvider.class);

        MainMenuManager mainMenuManager = new MainMenuManager(realmConfigurationProvider, allMenus, gameStateProvider);
        extendContext(MainMenuManager.class, mainMenuManager);
    }

    private static void extendContext(Class type, Object impl) {
        context.put(impl.getClass(), impl);
        context.put(type, impl);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> clazz) {
        T bean;

        try {
            bean = (T) context.get(clazz);
        } catch (Throwable t) {
            throw new DIException(t);
        }

        if (null == bean) {
            throw new DIException();
        }

        return bean;
    }
}
