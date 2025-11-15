package ru.mipt.bit.platformer.command;

import ru.mipt.bit.platformer.api.GameWorld;
import ru.mipt.bit.platformer.api.Shootable;

public class ShootCommand  implements Command {
    private final Shootable shootable;

    public ShootCommand(Shootable shootable) {
        this.shootable = shootable;
    }

    @Override
    public void execute(GameWorld world) {
        shootable.shoot(world);
    }
}
