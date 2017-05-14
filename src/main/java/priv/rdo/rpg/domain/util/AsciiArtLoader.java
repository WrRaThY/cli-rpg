package priv.rdo.rpg.domain.util;

import priv.rdo.rpg.adapters.util.io.InternalIO;

public class AsciiArtLoader {
    public static final String ASCII_ART_FOLDER = "ascii_art";

    public static String loadIfPossible(String characterName) {
        try {
            return InternalIO.readAsString(ASCII_ART_FOLDER, characterName);
        } catch (NullPointerException e) {
            return "";
        }
    }

    private AsciiArtLoader() {
    }
}
