package priv.rdo.rpg.ports.outgoing;

public interface BaseMenu<Type> {
    void showMessage(String message);

    void printAllOptions(String message);

    void printAllOptions();

    Type selectOption();

    Type showMenu();
}
