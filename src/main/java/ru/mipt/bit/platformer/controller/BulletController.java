package ru.mipt.bit.platformer.controller;

import ru.mipt.bit.platformer.model.BulletModel;
import ru.mipt.bit.platformer.util.TileMovement;
import ru.mipt.bit.platformer.view.Viewable;

public class BulletController extends EntityController<BulletModel, Viewable<BulletModel>> {
    private final TileMovement tileMovement;

    public BulletController(BulletModel entityModel, Viewable<BulletModel> viewable, TileMovement tileMovement) {
        super(entityModel, viewable);
        this.tileMovement = tileMovement;
    }

    @Override
    public void update(float delta) {
        entityModel.update(delta);
        tileMovement.moveRectangleBetweenTileCenters(
                viewable.getBounds(),
                entityModel.getPosition(),
                entityModel.getDestination(),
                entityModel.getMovementProgress()
        );
    }
}
