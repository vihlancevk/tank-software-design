package ru.mipt.bit.platformer.controller;

import ru.mipt.bit.platformer.model.ObstacleModel;
import ru.mipt.bit.platformer.view.ObstacleView;

public class ObstacleController extends EntityController<ObstacleModel, ObstacleView> {
    public ObstacleController(ObstacleModel entityModel, ObstacleView entityView) {
        super(entityModel, entityView);
    }
}
