package ru.mipt.bit.platformer.command;

import ru.mipt.bit.platformer.api.GameWorld;
import ru.mipt.bit.platformer.api.Movable;

public interface Command {
    void execute(GameWorld world, Movable entity);
}
