package ru.mipt.bit.platformer.controller;

import com.badlogic.gdx.graphics.g2d.Batch;
import ru.mipt.bit.platformer.model.EntityModel;
import ru.mipt.bit.platformer.view.Viewable;

public class EntityController<T extends EntityModel, V extends Viewable<T>> {
    protected final T entityModel;
    protected final V viewable;

    public EntityController(T entityModel, V viewable) {
        this.entityModel = entityModel;
        this.viewable = viewable;
    }

    public T getEntityModel() {
        return entityModel;
    }

    public void update(float delta) {
    }

    public void render(Batch batch) {
        viewable.render(batch, entityModel);
    }

    public void dispose() {
        viewable.dispose();
    }
}
