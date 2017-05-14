package priv.rdo.rpg.common.generator;

import priv.rdo.rpg.adapters.outgoing.SerializedRealmConfigurationProvider;
import priv.rdo.rpg.ports.exception.ConfigurationException;
import priv.rdo.rpg.ports.outgoing.dto.EnemyConfiguration;
import priv.rdo.rpg.ports.outgoing.dto.RealmConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import static priv.rdo.rpg.common.util.IntUtils.randomIntInclusive;

public class SerializationRealmConfigurationGenerator {
    public static void main(String[] args) throws ConfigurationException {
        generateRealms();
    }

    public static void generateRealms() throws ConfigurationException {
        SerializedRealmConfigurationProvider provider = new SerializedRealmConfigurationProvider();

        ArrayList<RealmConfiguration> realms = realms();
        provider.save(realms);
        realms.forEach(realm -> System.out.println(realm.detailedToString()));
    }

    public static ArrayList<RealmConfiguration> realms() {
        ArrayList<RealmConfiguration> realmConfigurations = new ArrayList<>();
        realmConfigurations.add(gta2Realm());
        realmConfigurations.add(lotrRealm());
        return realmConfigurations;
    }

    public static RealmConfiguration lotrRealm() {
        return buildRealmConfiguration("Lord of the rings", 15, SerializationRealmConfigurationGenerator::someUrukHai,
                buildEnemy("Sauron", 300, 13, 5),
                buildEnemy("Balrog", 200, 12, 3),
                buildEnemy("Saruman", 200, 13, 0),
                buildEnemy("Gollum", 30, 3, 0),
                buildEnemy("Nazgul", 150, 6, 5),
                buildEnemy("Melkor", 90, 7, 3),
                buildEnemy("Witch-king of Angmar", 60, 7, 2),
                buildEnemy("Smaug", 250, 15, 2)
        );
    }

    public static RealmConfiguration gta2Realm() {
        return buildRealmConfiguration("Grand Theft Auto", 14, SerializationRealmConfigurationGenerator::someGangster,
                buildEnemy("Johnny Zoo", 300, 15),
                buildEnemy("Trey Welsh", 200, 14),
                buildEnemy("Elmo", 250, 13),
                buildEnemy("Billy Bob Bean", 150, 12),
                buildEnemy("Dr. LaBrat", 170, 10),
                buildEnemy("Red Valdez", 120, 8),
                buildEnemy("Jerkov", 80, 6),
                buildEnemy("Sunbeam", 60, 4),
                buildEnemy("Uno Carb", 20, 2)
        );
    }

    private static EnemyConfiguration someUrukHai() {
        return buildEnemy("Uruk-Hai Warriors", "Wandering Uruk-Hai patrol", "WAAAAAAAGH!",
                randomIntInclusive(50, 250), randomIntInclusive(8, 13), randomIntInclusive(2));
    }

    private static EnemyConfiguration someGangster() {
        return buildEnemy("Gangsters", "Thugs looking for some trouble", "Give me your wallet or DIE!",
                randomIntInclusive(50, 250), randomIntInclusive(8, 13), randomIntInclusive(2));
    }

    public static EnemyConfiguration buildEnemy(String name, int hp, int damage) {
        return buildEnemy(name, hp, damage, damage / 5);
    }

    public static EnemyConfiguration buildEnemy(String name, int hp, int damage, int dmgVariation) {
        return buildEnemy(name, "empty for now", "I'll crush you like an insect!", hp, damage, dmgVariation);
    }

    public static EnemyConfiguration buildEnemy(String name, String desc, String greeting, int hp, int damage, int dmgVariation) {
        return new EnemyConfiguration(name, desc, greeting, hp, damage, dmgVariation);
    }

    public static RealmConfiguration buildRealmConfiguration(String name, int numberOFRandomEnemies,
                                                             Supplier<EnemyConfiguration> enemySupplier, EnemyConfiguration... definedEnemies) {
        List<EnemyConfiguration> enemies = randomEnemies(numberOFRandomEnemies, enemySupplier);
        enemies.addAll(Arrays.asList(definedEnemies));

        return new RealmConfiguration(name, enemies.size() - 10, enemies);
    }

    private static List<EnemyConfiguration> randomEnemies(int numberOFRandomEnemies, Supplier<EnemyConfiguration> enemySupplier) {
        List<EnemyConfiguration> randomEnemies = new ArrayList<>();
        for (int i = 0; i < numberOFRandomEnemies; i++) {
            randomEnemies.add(enemySupplier.get());
        }
        return randomEnemies;
    }
}
