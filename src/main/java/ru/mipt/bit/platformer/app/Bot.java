package ru.mipt.bit.platformer.app;

import com.badlogic.gdx.graphics.g2d.Batch;
import ru.mipt.bit.platformer.controller.PlayerController;

public class Bot {
    private final PlayerController controller;
    private float cooldown;

    public Bot(PlayerController controller) {
        this.controller = controller;
        this.cooldown = 0.0f;
    }

    public PlayerController getController() {
        return controller;
    }

    public float getCooldown() {
        return cooldown;
    }

    public void setCooldown(float cooldown) {
        this.cooldown = cooldown;
    }

    public void decreaseCooldown(float delta) {
        cooldown -= delta;
    }

    public void update(float delta) {
        controller.update(delta);
    }

    public void render(Batch batch) {
        controller.render(batch);
    }

    public void dispose() {
        controller.dispose();
    }
}
