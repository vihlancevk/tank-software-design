package ru.mipt.bit.platformer.command;

import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.api.GameWorld;
import ru.mipt.bit.platformer.api.Movable;

public class MoveCommand implements Command {
    private final Direction direction;

    public MoveCommand(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void execute(GameWorld world, Movable entity) {
        entity.move(world, direction);
    }
}
