package priv.rdo.rpg.domain.world.location;

import priv.rdo.rpg.common.util.ToStringBuilder;

import java.io.Serializable;
import java.util.Objects;

public class Coordinates implements Serializable {
    private final int x;
    private final int y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Coordinates up() {
        return new Coordinates(x, y - 1);
    }

    public Coordinates down() {
        return new Coordinates(x, y + 1);
    }

    public Coordinates left() {
        return new Coordinates(x - 1, y);
    }

    public Coordinates right() {
        return new Coordinates(x + 1, y);
    }

    @Override
    public String toString() {
        return ToStringBuilder.defaultBuilder(this)
                .append("x", x)
                .append("y", y)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinates)) return false;

        Coordinates that = (Coordinates) o;

        return x == that.x && y == that.y;
    }

    public boolean equals(int x, int y) {
        return this.x == x && this.y == y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }


}
