package ru.mipt.bit.platformer.command;

import ru.mipt.bit.platformer.api.GameWorld;
import ru.mipt.bit.platformer.api.Movable;
import ru.mipt.bit.platformer.model.Direction;

public class MoveRightCommand implements Command {
    private final Movable movable;

    public MoveRightCommand(Movable movable) {
        this.movable = movable;
    }

    @Override
    public void execute(GameWorld world) {
        movable.move(world, Direction.RIGHT);
    }
}
