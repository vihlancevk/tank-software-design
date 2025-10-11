package ru.mipt.bit.platformer.api;

import ru.mipt.bit.platformer.model.Direction;

public interface Movable {
    void move(GameWorld world, Direction direction);
    void update(float delta);
}
