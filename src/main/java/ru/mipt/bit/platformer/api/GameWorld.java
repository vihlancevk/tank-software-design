package ru.mipt.bit.platformer.api;

import ru.mipt.bit.platformer.controller.BulletController;

public interface GameWorld {
    boolean isAvailableForMove(int x, int y);

    void addBulletController(BulletController bulletController);
}
