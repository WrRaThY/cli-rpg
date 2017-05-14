package priv.rdo.rpg.domain.util;

import priv.rdo.rpg.domain.world.location.LocationType;

public class LegendBuilder extends OutputBuilderBase {
    private LegendBuilder() {
    }

    public static String buildLegend() {
        return new LegendBuilder().legend();
    }

    public String legend() {
        StringBuilder sb = new StringBuilder();

        for (LocationType locationType : LocationType.values()) {
            sb.append(toLegend(locationType)).append("\n");
        }

        return sb.toString();
    }

    private String toLegend(LocationType locationType) {
        return formatMapMark(locationType.getMapMark()) + " = " + locationType.getDescription();
    }

    private String formatMapMark(String mapMark) {
        return formatString("'" + mapMark + "'");
    }
}
