package ru.mipt.bit.platformer.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import ru.mipt.bit.platformer.model.EntityModel;

public interface Viewable<T extends EntityModel> {
    void render(Batch batch, T entityModel);

    void dispose();
}
