package priv.rdo.rpg.domain.character

import priv.rdo.rpg.test.util.CommonTestObjects
import spock.lang.Specification

/**
 * @author WrRaThY
 * @since 10.05.2017
 */
class PlayerTest extends Specification {


    def "should create a new player with a proper toString representation based on user input and base stats"() {
        given:
            Player player = CommonTestObjects.testPlayer()

        when:
            String playerToString = player.toString()

        then:
            println player //print out for silly humans to see something
            player.getName() == CommonTestObjects.PLAYER_NAME
            player.getDescription() == CommonTestObjects.PLAYER_DESC
            player.getMaxHp() == PlayerBaseStatistics.BASE_HP + CommonTestObjects.HP_BONUS
            player.getDamage() == PlayerBaseStatistics.BASE_DMG + CommonTestObjects.DMG_BONUS
            player.getDamageVariation() == PlayerBaseStatistics.BASE_DMG_VARIATION + CommonTestObjects.DMG_VARIATION_BONUS

        and:
            playerToString ==
                    "\tname: Player\n" +
                    "\tdescription: PlayerDesc\n" +
                    "\thealth: 101/101\n" +
                    "\tdamage: 14-16\n" +
                    "\tdamageMitigation: 0\n" +
                    "\tcoordinates: [x=1,y=0]\n" +
                    "\texperience: level=1, currentExp=0/100\n"
    }
}
