package priv.rdo.rpg.common.util;

//TODO: composite to make fixing formats easier. (bold red etc)
public enum Color {

    CLEAR_ALL_FORMATTING(0),

    BOLD(1),
    ITALIC(3),
    UNDERLINE(4),

    BLACK(30),
    RED(31),
    GREEN(32),
    YELLOW(33),
    BLUE(34),
    MAGENTA(35),
    CYAN(36),
    WHITE(37),

    BLACK_BG(40),
    RED_BG(41),
    GREEN_BG(42),
    YELLOW_BG(43),
    BLUE_BG(44),
    MAGENTA_BG(45),
    CYAN_BG(46),
    WHITE_BG(47),;

    private final int colorNumber;

    private Color(int colorNumber) {
        this.colorNumber = colorNumber;
    }

    public int number() {
        return colorNumber;
    }

    public String format(String input) {
        return (char) 27 + "[" + number() + "m" + input + (char) 27 + "[" + CLEAR_ALL_FORMATTING.number() + "m";
    }

    @Override
    public String toString() {
        return name() + "(" + colorNumber + ")";
    }
}