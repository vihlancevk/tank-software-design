package ru.mipt.bit.platformer.controller;

import com.badlogic.gdx.graphics.g2d.Batch;
import ru.mipt.bit.platformer.model.EntityModel;
import ru.mipt.bit.platformer.view.EntityView;

public class EntityController<T extends EntityModel, V extends EntityView<T>> {
    protected final T entityModel;
    protected final V entityView;

    public EntityController(T entityModel, V entityView) {
        this.entityModel = entityModel;
        this.entityView = entityView;
    }

    public T getEntityModel() {
        return entityModel;
    }

    public void update(float delta) {
    }

    public void render(Batch batch) {
        entityView.render(batch, entityModel);
    }

    public void dispose() {
        entityView.dispose();
    }
}
