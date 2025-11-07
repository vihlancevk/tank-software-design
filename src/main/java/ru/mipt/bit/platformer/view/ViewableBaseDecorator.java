package ru.mipt.bit.platformer.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import ru.mipt.bit.platformer.model.EntityModel;

public class ViewableBaseDecorator<T extends EntityModel> implements Viewable<T> {
    private final Viewable<T> viewable;

    public ViewableBaseDecorator(Viewable<T> viewable) {
        this.viewable = viewable;
    }

    @Override
    public void render(Batch batch, T entityModel) {
        viewable.render(batch, entityModel);
    }

    @Override
    public void dispose() {
        viewable.dispose();
    }
}
