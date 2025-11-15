package ru.mipt.bit.platformer.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.model.EntityModel;

public class ViewableBaseDecorator<T extends EntityModel> implements Viewable<T> {
    protected final Viewable<T> viewable;

    public ViewableBaseDecorator(Viewable<T> viewable) {
        this.viewable = viewable;
    }

    @Override
    public boolean isHealthVisible() {
        return viewable.isHealthVisible();
    }

    @Override
    public void setHealthVisible(boolean healthVisible) {
        viewable.setHealthVisible(healthVisible);
    }

    @Override
    public Rectangle getBounds() {
        return viewable.getBounds();
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
