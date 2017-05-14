package priv.rdo.rpg.adapters.util.menu;

public class MenuToStringFormatter<ItemType> {

    private static final String TAB = "\t";
    private static final String SEPARATOR = ": ";

    public MenuToStringFormatter() {
    }

    public String format(ItemType item, int itemNumberInList) {
        return format(item, userReadableItemNumber(itemNumberInList));
    }

    public String format(ItemType item, String itemKey) {
        if (null == item) {
            return errorMessage();
        }
        return format(item.toString(), itemKey);
    }

    public String format(String itemToString, String itemKey) {
        if (null == itemToString) {
            return errorMessage();
        }
        return formattedItem(itemToString, itemKey);
    }

    private String formattedItem(String itemToString, String itemKey) {
        return TAB + itemKey + SEPARATOR + itemToString;
    }

    private String errorMessage() {
        return TAB + "Weird stuff happened, item vanished!";
    }

    private String userReadableItemNumber(int itemNumberInList) {
        return String.valueOf(itemNumberInList + 1);
    }
}
