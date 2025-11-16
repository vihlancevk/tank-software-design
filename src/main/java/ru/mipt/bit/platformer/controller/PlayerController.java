package ru.mipt.bit.platformer.controller;

import ru.mipt.bit.platformer.api.GameWorld;
import ru.mipt.bit.platformer.api.Healthable;
import ru.mipt.bit.platformer.api.Movable;
import ru.mipt.bit.platformer.api.Shootable;
import ru.mipt.bit.platformer.model.BulletModel;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.PlayerModel;
import ru.mipt.bit.platformer.util.TileMovement;
import ru.mipt.bit.platformer.view.BulletView;
import ru.mipt.bit.platformer.view.Viewable;

import java.util.Optional;

public class PlayerController extends EntityController<PlayerModel, Viewable<PlayerModel>> implements Movable, Shootable, Healthable {
    private final TileMovement tileMovement;

    public PlayerController(PlayerModel playerModel, Viewable<PlayerModel> viewable, TileMovement tileMovement) {
        super(playerModel, viewable);
        this.tileMovement = tileMovement;
    }

    @Override
    public void update(float delta) {
        tileMovement.moveRectangleBetweenTileCenters(
                viewable.getBounds(),
                entityModel.getPosition(),
                entityModel.getDestination(),
                entityModel.getMovementProgress()
        );
        entityModel.update(delta);
    }

    @Override
    public void move(GameWorld gameWorld, Direction direction) {
        entityModel.move(gameWorld, direction);
    }

    @Override
    public void shoot(GameWorld gameWorld) {
        Optional<BulletModel> optionalBulletModel = entityModel.shoot();
        if (optionalBulletModel.isEmpty()) {
            return;
        }
        BulletModel bulletModel = optionalBulletModel.get();
        BulletView bulletView = BulletView.create();
        gameWorld.addBulletController(new BulletController(bulletModel, bulletView, tileMovement));
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
