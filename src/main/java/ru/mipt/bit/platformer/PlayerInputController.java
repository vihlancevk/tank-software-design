package ru.mipt.bit.platformer;

import com.badlogic.gdx.Gdx;

import java.util.Optional;

import static com.badlogic.gdx.Input.Keys.*;

public class PlayerInputController {
    public Optional<Direction> getDirection() {
        if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W)) {
            return Optional.of(Direction.UP);
        }
        if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S)) {
            return Optional.of(Direction.DOWN);
        }
        if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A)) {
            return Optional.of(Direction.LEFT);
        }
        if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D)) {
            return Optional.of(Direction.RIGHT);
        }
        return Optional.empty();
    }
}
