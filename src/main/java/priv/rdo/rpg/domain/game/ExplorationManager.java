package priv.rdo.rpg.domain.game;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import priv.rdo.rpg.domain.character.NPC;
import priv.rdo.rpg.domain.character.Player;
import priv.rdo.rpg.domain.common.exception.ExplorationException;
import priv.rdo.rpg.domain.common.exception.ShouldNeverHappen;
import priv.rdo.rpg.domain.menu.AllMenus;
import priv.rdo.rpg.domain.util.AsciiArtLoader;
import priv.rdo.rpg.domain.world.World;
import priv.rdo.rpg.domain.world.location.Coordinates;
import priv.rdo.rpg.domain.world.location.Location;
import priv.rdo.rpg.ports.exception.ConfigurationException;
import priv.rdo.rpg.ports.outgoing.ExplorationMenu;
import priv.rdo.rpg.ports.outgoing.GameStateProvider;
import priv.rdo.rpg.ports.outgoing.dto.ExplorationMenuItem;
import priv.rdo.rpg.ports.outgoing.dto.GameState;

import static priv.rdo.rpg.domain.common.exception.Victory.victory;
import static priv.rdo.rpg.domain.game.StaticMessages.GAME_SAVED;
import static priv.rdo.rpg.domain.game.StaticMessages.TRAVEL_INFO;
import static priv.rdo.rpg.domain.util.LegendBuilder.buildLegend;
import static priv.rdo.rpg.domain.util.StatisticsBuilder.buildStatistics;
import static priv.rdo.rpg.domain.util.WorldViewBuilder.buildWorldView;

public class ExplorationManager {
    private static final Logger LOG = LogManager.getLogger(ExplorationManager.class);

    private final GameStateProvider gameStateProvider;
    private final ExplorationMenu explorationMenu;
    private final AllMenus allMenus;
    private final World world;
    private final Player player;


    public ExplorationManager(GameStateProvider gameStateProvider, AllMenus allMenus, World world, Player player) {
        this.gameStateProvider = gameStateProvider;
        this.explorationMenu = allMenus.explorationMenu();
        this.allMenus = allMenus;
        this.world = world;
        this.player = player;
    }

    public void startExploring() throws ConfigurationException {
        LOG.traceEntry();
        ExplorationMenuItem choice = explorationMenu.showMenu();

        while (ExplorationMenuItem.EXIT != choice) {
            LOG.debug("{} selected", choice);

            switch (choice) {
                case UP:
                    travel(player.up());
                    break;
                case DOWN:
                    travel(player.down());
                    break;
                case LEFT:
                    travel(player.left());
                    break;
                case RIGHT:
                    travel(player.right());
                    break;
                case COMMANDS:
                    explorationMenu.printAllOptions();
                    break;
                case MAP:
                    showMap();
                    break;
                case LEGEND:
                    showLegend();
                    break;
                case PLAYER:
                    explorationMenu.showMessage(player.toStringWithColors());
                    break;
                case STATS:
                    explorationMenu.showStatistics(buildStatistics(world, player));
                    break;
                case SAVE:
                    saveGame();
                    break;
                case EXIT:
                    throw new ShouldNeverHappen();
                default:
                    throw new ShouldNeverHappen();
            }

            choice = explorationMenu.selectOption();
        }

        //TODO: ask about saving the game before leaving

        LOG.traceExit();
    }

    void showMap() {
        LOG.traceEntry();
        explorationMenu.showMap(buildWorldView(world, player));
        LOG.traceExit();
    }

    //TODO: figure out how to use AOP without Spring AOP... logging entry and exit gets annoying...
    void showLegend() {
        LOG.traceEntry();
        explorationMenu.showMessage(buildLegend());
        LOG.traceExit();
    }

    void travel(Coordinates coordinates) {
        LOG.traceEntry();
        try {
            Location newLocation = world.getLocation(coordinates);
            if (newLocation.isAnyoneThere()) {
                interactWithNpc(newLocation);
            } else {
                moveToEmptySpace(newLocation);
            }
        } catch (ExplorationException e) {
            explorationMenu.showMessage(e.getMessage());
        }
        showMap();
        LOG.traceExit();
    }

    void interactWithNpc(Location newLocation) {
        LOG.traceEntry();
        NPC npc = newLocation.getNpc();
        explorationMenu.showMessage(AsciiArtLoader.loadIfPossible(npc.getName()));
        explorationMenu.showMessage(npc.toStringWithColors());
        explorationMenu.showMessage(npc.greeting());
        if (npc.isEnemy()) {
            fight(newLocation);

            if (world.allEnemiesDead()){
                victory(world, player);
            }
        }
        LOG.traceExit();
    }

    void fight(Location newLocation) {
        new FightManager(allMenus, player, newLocation).fight();
    }

    void moveToEmptySpace(Location newLocation) {
        LOG.traceEntry();
        player.setCoordinates(newLocation.getCoordinates());
        explorationMenu.showMessage(TRAVEL_INFO + newLocation.desc());
        LOG.traceExit();
    }

    void saveGame() throws ConfigurationException {
        LOG.traceEntry();
        gameStateProvider.saveGame(new GameState(world, player));
        explorationMenu.showMessage(GAME_SAVED);
        LOG.traceExit();
    }

}
