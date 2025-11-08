package ru.mipt.bit.platformer.controller;

import ru.mipt.bit.platformer.api.GameWorld;
import ru.mipt.bit.platformer.api.Healthable;
import ru.mipt.bit.platformer.api.Movable;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.PlayerModel;
import ru.mipt.bit.platformer.view.Viewable;

public class PlayerController extends EntityController<PlayerModel, Viewable<PlayerModel>> implements Movable, Healthable {
    public PlayerController(PlayerModel entityModel, Viewable<PlayerModel> viewable) {
        super(entityModel, viewable);
    }

    @Override
    public void move(GameWorld world, Direction direction) {
        entityModel.move(world, direction);
    }

    @Override
    public void update(float delta) {
        entityModel.update(delta);
    }

    @Override
    public boolean isHealthVisible() {
        return viewable.isHealthVisible();
    }

    @Override
    public void setHealthVisible(boolean healthVisible) {
        viewable.setHealthVisible(healthVisible);
    }
}
