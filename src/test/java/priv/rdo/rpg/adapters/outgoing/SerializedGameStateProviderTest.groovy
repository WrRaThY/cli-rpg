package priv.rdo.rpg.adapters.outgoing

import priv.rdo.rpg.domain.character.Player
import priv.rdo.rpg.domain.world.World
import priv.rdo.rpg.ports.outgoing.dto.GameState
import priv.rdo.rpg.test.util.CommonTestObjects
import spock.lang.Specification

/**
 * @author WrRaThY
 * @since 12.05.2017
 */
class SerializedGameStateProviderTest extends Specification {

    def "loaded game state should be exactly the same as the saved one"() {
        given:
            World worldToSave = CommonTestObjects.testWorld()
            Player playerToSave = CommonTestObjects.testPlayer()
            GameState gameStateToSave = new GameState(worldToSave, playerToSave)

        and: "I know this is a bad idea, but its already late... "
            SerializedGameStateProvider sut = Spy {
                filename() >> "testFileName.ser"
            }

        when:
            sut.saveGame(gameStateToSave)

        and:
            GameState loadedGameState = sut.loadGame()
            Player loadedPlayer = loadedGameState.getPlayer()
            World loadedWorld = loadedGameState.getWorld()

        then:
            noExceptionThrown()
            loadedPlayer.toString() == playerToSave.toString()
            loadedWorld.toString() == worldToSave.toString()
            //TODO: comparing toStrings is good enough for now, but should be fixed when the time comes
    }
}
