package priv.rdo.rpg.domain.game;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import priv.rdo.rpg.domain.character.Player;
import priv.rdo.rpg.domain.common.exception.LoadGameException;
import priv.rdo.rpg.domain.common.exception.PlayerDied;
import priv.rdo.rpg.domain.common.exception.Victory;
import priv.rdo.rpg.domain.menu.AllMenus;
import priv.rdo.rpg.domain.world.World;
import priv.rdo.rpg.ports.exception.ConfigurationException;
import priv.rdo.rpg.ports.outgoing.GameStateProvider;
import priv.rdo.rpg.ports.outgoing.MainMenu;
import priv.rdo.rpg.ports.outgoing.PlayerConfigurationMenu;
import priv.rdo.rpg.ports.outgoing.WorldConfigurationMenu;
import priv.rdo.rpg.ports.outgoing.dto.GameState;
import priv.rdo.rpg.ports.outgoing.dto.PlayerConfiguration;
import priv.rdo.rpg.ports.outgoing.dto.RealmConfiguration;

import java.util.List;

import static priv.rdo.rpg.domain.game.StaticMessages.CREDITS;
import static priv.rdo.rpg.domain.game.StaticMessages.GAME_LOADED;
import static priv.rdo.rpg.domain.game.StaticMessages.INTRODUCTION;
import static priv.rdo.rpg.domain.game.StaticMessages.REALM_QUESTION;
import static priv.rdo.rpg.domain.game.StaticMessages.greeting;
import static priv.rdo.rpg.domain.game.StaticMessages.questionsToPlayer;
import static priv.rdo.rpg.domain.game.StaticMessages.realmConfigDone;
import static priv.rdo.rpg.domain.util.WorldViewBuilder.buildWorldView;

//TODO: add tests
public class GameManager {
    private static final Logger LOG = LogManager.getLogger(GameManager.class);

    private final GameStateProvider gameStateProvider;
    private final AllMenus allMenus;

    private final World world;
    private final Player player;

    public static void newGame(GameStateProvider gameStateProvider, AllMenus allMenus, List<RealmConfiguration> realmConfig) throws ConfigurationException {
        GameManager gameManager = new GameManager(gameStateProvider, allMenus, realmConfig);
        gameManager.startGame();
    }

    private GameManager(GameStateProvider gameStateProvider, AllMenus allMenus, List<RealmConfiguration> realmConfig) throws ConfigurationException {
        this.gameStateProvider = gameStateProvider;
        this.allMenus = allMenus;

        this.world = initWorld(realmConfig);
        this.player = initPlayer();
    }

    public static void loadGame(GameStateProvider gameStateProvider, AllMenus allMenus) throws ConfigurationException {
        try {
            GameManager gameManager = new GameManager(gameStateProvider, allMenus);
            gameManager.startGame();
        } catch (LoadGameException e) {
            allMenus.mainMenu().showMessage(e.getMessage());
        }

    }

    public GameManager(GameStateProvider gameStateProvider, AllMenus allMenus) throws ConfigurationException {
        this.gameStateProvider = gameStateProvider;
        this.allMenus = allMenus;

        GameState loadedGame = gameStateProvider.loadGame();
        this.world = loadedGame.getWorld();
        this.player = loadedGame.getPlayer();

        allMenus.explorationMenu().showMessage(GAME_LOADED);
        allMenus.explorationMenu().showMap(buildWorldView(world, player));
    }

    private World initWorld(List<RealmConfiguration> realmConfigs) throws ConfigurationException {
        WorldConfigurationMenu worldConfigMenu = allMenus.worldConfigMenu();

        RealmConfiguration realmConfig = worldConfigMenu.chooseConfiguration(REALM_QUESTION, realmConfigs);

        World world = new World(realmConfig);
        worldConfigMenu.confirmRealmConfiguration(realmConfigDone(world));

        return world;
    }

    private Player initPlayer() {
        PlayerConfigurationMenu playerConfigMenu = allMenus.playerConfigMenu();

        playerConfigMenu.showIntroduction(INTRODUCTION);
        PlayerConfiguration playerConfig = playerConfigMenu.askForPlayerConfig(questionsToPlayer());

        Player player = new Player(playerConfig, world.randomCoordinatesWithoutAnyone());
        playerConfigMenu.greetPlayer(greeting(player, world));

        return player;
    }

    void startGame() throws ConfigurationException {
        LOG.traceEntry();
        MainMenu mainMenu = allMenus.mainMenu();

        ExplorationManager explorationManager = new ExplorationManager(gameStateProvider, allMenus, world, player);
        try {
            explorationManager.startExploring();
        } catch (Victory e) {
            mainMenu.showMessage(e.getMessage());
            mainMenu.showMessage(CREDITS);
        } catch (PlayerDied e) {
            mainMenu.showMessage(e.getMessage());
        }

        LOG.traceExit();
    }
}
