package priv.rdo.rpg.common.util;

import java.util.concurrent.ThreadLocalRandom;

public class IntUtils {
    private IntUtils() {
    }

    public static int randomIntInclusive(int maxInclusive) {
        return randomIntInclusive(0, maxInclusive);
    }

    public static int randomIntInclusive(int minInclusive, int maxInclusive) {
        return ThreadLocalRandom.current().nextInt(minInclusive, maxInclusive + 1);
    }

    public static int randomIntExclusive(int maxExclusive) {
        return randomIntExclusive(0, maxExclusive);
    }

    public static int randomIntExclusive(int minInclusive, int maxExclusive) {
        return ThreadLocalRandom.current().nextInt(minInclusive, maxExclusive);
    }

    public static boolean isNotBetweenZeroAndMaxExclusive(int tested, int maxExclusive) {
        return !isBetweenZeroAndMaxExclusive(tested, maxExclusive);
    }

    public static boolean isBetweenZeroAndMaxExclusive(int tested, int maxExclusive) {
        return isWithinRangeExclusive(tested, 0, maxExclusive);
    }

    public static boolean isNotWithinRangeExclusive(int tested, int minInclusive, int maxExclusive) {
        return !isWithinRangeExclusive(tested, minInclusive, maxExclusive);
    }

    public static boolean isWithinRangeExclusive(int tested, int minInclusive, int maxExclusive) {
        return tested >= minInclusive && tested < maxExclusive;
    }

    public static boolean isWithinRangeInclusive(int tested, int minInclusive, int maxInclusive) {
        return tested >= minInclusive && tested <= maxInclusive;
    }
}
