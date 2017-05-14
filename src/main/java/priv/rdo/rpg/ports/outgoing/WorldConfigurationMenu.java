package priv.rdo.rpg.ports.outgoing;

import priv.rdo.rpg.ports.outgoing.dto.RealmConfiguration;

import java.util.List;

public interface WorldConfigurationMenu {
    RealmConfiguration chooseConfiguration(String realmQuestion, List<RealmConfiguration> realmConfigs);

    void confirmRealmConfiguration(String realmConfirmationMessage);
}
