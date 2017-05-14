package priv.rdo.rpg.domain.util;

import priv.rdo.rpg.domain.character.Player;
import priv.rdo.rpg.domain.world.World;
import priv.rdo.rpg.domain.world.location.Location;
import priv.rdo.rpg.domain.world.location.LocationType;

public class WorldViewBuilder extends GameInfoBuilder<WorldViewBuilder> {

    public static String buildWorldView(World world, Player player) {
        return aWorldView().withWorld(world).withPlayer(player).build();
    }

    public static String buildWorldView(World world) {
        return aWorldView().withWorld(world).build();
    }

    public static WorldViewBuilder aWorldView() {
        return new WorldViewBuilder();
    }

    @Override
    protected WorldViewBuilder that() {
        return this;
    }

    @Override
    protected String buildInner() {
        return mapView();
    }

    /*
     * TODO: this is ugly as hell.
     * programmer cried when he wrote it
     * FIX IT!
     */
    String mapView() {
        StringBuilder sb = new StringBuilder();
        Location[][] map = world.getMap();

        appendColsHeader(sb, map[0].length);
        for (int y = 0; y < map.length; y++) {
            appendRowsHeader(sb, y);
            for (int x = 0; x < map[y].length; x++) {
                if (playerIsHere(player, x, y)) {
                    appendPlayerMark(sb);
                } else {
                    appendLocationMark(sb, map[x][y]);
                }

            }
            appendRowsHeader(sb, y);
            sb.append("\n");
        }
        appendColsHeader(sb, map[0].length);

        return sb.toString();

    }

    private void appendColsHeader(StringBuilder sb, int colsNumber) {
        sb.append(formatString(" "));
        for (int i = 0; i < colsNumber; i++) {
            sb.append(formatDigit(i));
        }
        sb.append("\n");
    }

    private void appendRowsHeader(StringBuilder sb, int y) {
        sb.append(formatDigit(y));
    }

    private boolean playerIsHere(Player player, int x, int y) {
        return player != null && player.getCoordinates() != null && player.getCoordinates().equals(x, y);
    }

    private void appendPlayerMark(StringBuilder sb) {
        sb.append(formatLocationMark(LocationType.PLAYER.getMapMark()));
    }

    private void appendLocationMark(StringBuilder sb, Location location) {
        sb.append(formatLocationMark(location.mapMark()));
    }

}
