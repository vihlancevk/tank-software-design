package ru.mipt.bit.platformer.api;

import ru.mipt.bit.platformer.controller.EntityController;

public interface EntityControllerFactory {
    <T extends  EntityController<?, ?>> T createEntity(String type, String texturePath, int x, int y, float speed);
}
