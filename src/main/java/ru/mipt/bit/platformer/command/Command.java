package ru.mipt.bit.platformer.command;

import ru.mipt.bit.platformer.controller.LevelController;
import ru.mipt.bit.platformer.controller.PlayerController;

public interface Command {
    void execute(LevelController levelController, PlayerController playerController);
}
