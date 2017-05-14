package priv.rdo.rpg.ports.outgoing;

import priv.rdo.rpg.ports.exception.ConfigurationException;
import priv.rdo.rpg.ports.outgoing.dto.GameState;

import java.util.Collections;
import java.util.List;

/**
 * @author WrRaThY
 * @since 13.05.2017
 */
public interface ResourceProvider<ItemType> {
    List<ItemType> load() throws ConfigurationException;

    void save(List<ItemType> resource) throws ConfigurationException;

    default ItemType loadOne() throws ConfigurationException {
        List<ItemType> load = load();
        if (load.size() > 1) {
            throw new ConfigurationException("Only one entry permitted for this resource");
        }

        return load.get(0);
    }

    default void saveOne(ItemType resource) throws ConfigurationException {
        List<ItemType> listWithOneResource = Collections.singletonList(resource);
        save(listWithOneResource);
    }
}
