package ru.mipt.bit.platformer.controller;

import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.api.GameWorld;
import ru.mipt.bit.platformer.api.Movable;
import ru.mipt.bit.platformer.model.PlayerModel;
import ru.mipt.bit.platformer.view.PlayerView;

public class PlayerController extends EntityController<PlayerModel, PlayerView> implements Movable {
    public PlayerController(PlayerModel entityModel, PlayerView entityView) {
        super(entityModel, entityView);
    }

    @Override
    public void move(GameWorld world, Direction direction) {
        entityModel.move(world, direction);
    }

    @Override
    public void update(float delta) {
        entityModel.update(delta);
    }
}
