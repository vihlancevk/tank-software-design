package ru.mipt.bit.platformer.command;

import ru.mipt.bit.platformer.Direction;
import ru.mipt.bit.platformer.controller.LevelController;
import ru.mipt.bit.platformer.controller.PlayerController;

public class MoveCommand implements Command {
    private final Direction direction;

    public MoveCommand(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void execute(LevelController levelController, PlayerController playerController) {
        playerController.move(levelController, direction);
    }
}
