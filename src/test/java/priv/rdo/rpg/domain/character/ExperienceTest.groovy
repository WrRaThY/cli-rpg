package priv.rdo.rpg.domain.character

import spock.lang.Specification
import spock.lang.Unroll

/**
 * @author WrRaThY
 * @since 12.05.2017
 */
class ExperienceTest extends Specification {
    @Unroll
    def "should calculate expectedExpToLevelUp correctly: #desc"() {
        expect:
            exp.getExpRequiredToLevelUp() == expectedExpToLevelUp
            exp.level == expectedLevel

        where:
            exp                      | expectedExpToLevelUp | expectedLevel | desc
            freshPlayer()            | 100                  | 1             | "fresh Player"
            levelUpOnce()            | 210                  | 2             | "levelUp Once"
            levelUpTwoTimes()        | 441                  | 3             | "levelUp Two Times"
            levelUpTwoLevelsAtOnce() | 441                  | 3             | "levelUp Two Levels At Once"
            levelUpThenDont()        | 210                  | 2             | "levelUp Then Dont"
    }

    def freshPlayer() {
        new Experience()
    }

    def levelUpOnce() {
        Experience experience = new Experience()
        experience.addKillReward(150)
        experience
    }

    def levelUpTwoTimes() {
        Experience experience = new Experience()
        experience.addKillReward(150)
        experience.addKillReward(150)
        experience
    }

    def levelUpTwoLevelsAtOnce() {
        Experience experience = new Experience()
        experience.addKillReward(250)
        experience
    }

    def levelUpThenDont() {
        Experience experience = new Experience()
        experience.addKillReward(150)
        experience.addKillReward(10)
        experience
    }


}
