package priv.rdo.rpg.ports.outgoing;

import priv.rdo.rpg.ports.outgoing.dto.ExplorationMenuItem;

public interface ExplorationMenu extends BaseMenu<ExplorationMenuItem> {

    //TODO: change to some DTO, so client side may choose the rendering option
    void showMap(String map);

    //TODO: change to some DTO, so client side may choose the rendering option
    void showStatistics(String statistics);
}
