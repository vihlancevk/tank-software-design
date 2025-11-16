package ru.mipt.bit.platformer.model;

import java.util.HashMap;
import java.util.Map;

public enum Direction {
    UP(0, 1, 90),
    DOWN(0, -1, -90),
    LEFT(-1, 0, 180),
    RIGHT(1, 0, 0);

    public final int dx;
    public final int dy;
    public final int rotation;

    private static final Map<Integer, Direction> BY_ROTATION = new HashMap<>();

    static {
        for (Direction d : values()) {
            BY_ROTATION.put(d.rotation, d);
        }
    }

    Direction(int dx, int dy, int rotation) {
        this.dx = dx;
        this.dy = dy;
        this.rotation = rotation;
    }

    public static Direction fromRotation(int rotation) {
        Direction direction = BY_ROTATION.get(rotation);
        if (direction == null) {
            throw new IllegalArgumentException("Invalid rotation: " + rotation);
        }
        return direction;
    }
}
