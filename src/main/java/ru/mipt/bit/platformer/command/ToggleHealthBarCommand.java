package ru.mipt.bit.platformer.command;

import ru.mipt.bit.platformer.api.GameWorld;
import ru.mipt.bit.platformer.api.Healthable;

public class ToggleHealthBarCommand implements Command {
    private final Healthable healthable;

    public ToggleHealthBarCommand(Healthable healthable) {
        this.healthable = healthable;
    }

    @Override
    public void execute(GameWorld world) {
        healthable.setHealthVisible(!healthable.isHealthVisible());
    }
}
