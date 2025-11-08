package ru.mipt.bit.platformer.controller;

import ru.mipt.bit.platformer.model.ObstacleModel;
import ru.mipt.bit.platformer.view.Viewable;

public class ObstacleController extends EntityController<ObstacleModel, Viewable<ObstacleModel>> {
    public ObstacleController(ObstacleModel entityModel, Viewable<ObstacleModel> viewable) {
        super(entityModel, viewable);
    }
}
