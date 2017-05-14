package priv.rdo.rpg.domain.world;

import priv.rdo.rpg.common.util.ToStringBuilder;
import priv.rdo.rpg.domain.character.Ally;
import priv.rdo.rpg.domain.character.Enemy;
import priv.rdo.rpg.domain.character.NPC;
import priv.rdo.rpg.domain.common.exception.ExplorationException;
import priv.rdo.rpg.domain.world.location.Coordinates;
import priv.rdo.rpg.domain.world.location.Location;
import priv.rdo.rpg.ports.outgoing.dto.EnemyConfiguration;
import priv.rdo.rpg.ports.outgoing.dto.RealmConfiguration;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static priv.rdo.rpg.common.util.IntUtils.isNotBetweenZeroAndMaxExclusive;
import static priv.rdo.rpg.common.util.IntUtils.randomIntExclusive;

public class World implements Serializable {
    private final String name;
    private final Map<Coordinates, Ally> allies;
    private final Map<Coordinates, Enemy> enemies;

    //TODO: maybe this should not be an array of arrays at all? maybe it should be a Map? Map<Coordinate, Location> something to think about
    private final Location[][] map;

    public World(RealmConfiguration realmConfiguration) {
        this.name = realmConfiguration.getName();
        this.allies = new HashMap<>();
        this.enemies = new HashMap<>();
        this.map = new Location[realmConfiguration.getRealmSize()][realmConfiguration.getRealmSize()];

        this.initWorld(realmConfiguration.getEnemyConfiguration());
    }

    private void initWorld(List<EnemyConfiguration> enemyConfiguration) {
        initAllies();
        initEnemies(enemyConfiguration);
        initMap();
    }

    private void initAllies() {
        Ally ally = new Ally("Wise Man", "He knows everything",
                "The answer to all of your questions is is 8. 8 is everything. Apart from that... To win this game you must end all enemies",
                9999, 1, 0);
        Coordinates coordinates = new Coordinates(0, 0);
        allies.put(coordinates, ally);
    }

    void initEnemies(List<EnemyConfiguration> enemyConfiguration) {
        for (EnemyConfiguration configuration : enemyConfiguration) {
            Enemy enemy = new Enemy(configuration);
            Coordinates enemyCoordinates = randomCoordinatesWithoutAnyone();
            enemies.put(enemyCoordinates, enemy);
        }
    }

    void initMap() {
        for (int x = 0; x < map.length; x++) {
            Location[] column = map[x];
            for (int y = 0; y < column.length; y++) {
                Coordinates currentCoordinates = new Coordinates(x, y);
                NPC someone = searchForNpc(currentCoordinates);
                if (null != someone) {
                    map[x][y] = locationWithNpc(currentCoordinates, someone);
                } else {
                    map[x][y] = emptyLocation(currentCoordinates);
                }
            }
        }
    }

    private NPC searchForNpc(Coordinates currentCoordinates) {
        if (enemies.containsKey(currentCoordinates)) {
            return enemies.get(currentCoordinates);
        } else {
            return allies.get(currentCoordinates);
        }
    }

    private Location locationWithNpc(Coordinates coordinates, NPC someone) {
        return new Location(coordinates, someone);
    }

    private Location emptyLocation(Coordinates coordinates) {
        return new Location(coordinates);
    }

    //TODO: think about a better way to do this...
    public Coordinates randomCoordinatesWithoutAnyone() {
        Coordinates coordinates;

        do {
            coordinates = randomCoordinates(map.length);
        } while (someoneIsThere(coordinates));

        return coordinates;
    }

    private boolean someoneIsThere(Coordinates coordinates) {
        return enemies.containsKey(coordinates) || allies.containsKey(coordinates);
    }

    static Coordinates randomCoordinates(int mapSize) {
        int randomX = randomIntExclusive(mapSize);
        int randomY = randomIntExclusive(mapSize);
        return new Coordinates(randomX, randomY);
    }


    public Location getLocation(Coordinates coordinates) throws ExplorationException {
        return getLocation(coordinates.getX(), coordinates.getY());
    }

    public Location getLocation(int x, int y) throws ExplorationException {
        validateCoordinates(x, y);
        return map[x][y];
    }

    private void validateCoordinates(int x, int y) throws ExplorationException {
        validateCoordinate(x);
        validateCoordinate(y);
    }

    private void validateCoordinate(int index) throws ExplorationException {
        if (isNotBetweenZeroAndMaxExclusive(index, mapSize())) {
            ExplorationException.cannotGo(index);
        }
    }

    public boolean allEnemiesDead() {
        return aliveEnemiesLeft() == 0;
    }

    public long aliveEnemiesLeft() {
        return enemies.values().stream().filter(Enemy::isAlive).count();
    }

    public String getName() {
        return name;
    }

    public Location[][] getMap() {
        return map;
    }

    public Map<Coordinates, Enemy> getEnemies() {
        return enemies;
    }

    public int mapSize() {
        return map.length;
    }

    @Override
    public String toString() {
        return ToStringBuilder.defaultBuilder(this)
                .append("name", name)
                .append("mapSize", mapSize())
                .build();
    }

}
