package ru.mipt.bit.platformer.app;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.api.GameWorld;
import ru.mipt.bit.platformer.api.Movable;
import ru.mipt.bit.platformer.command.Command;

import java.util.HashMap;
import java.util.Map;

public class InputHandler {
    private final Map<Integer, Command> key2command;

    public InputHandler() {
        key2command = new HashMap<>();
    }

    public void setCommand(int key, Command command) {
        key2command.put(key, command);
    }

    public void handleInput(GameWorld gameWorld, Movable movable) {
        for (Map.Entry<Integer, Command> entry : key2command.entrySet()) {
            if (Gdx.input.isKeyPressed(entry.getKey())) {
                entry.getValue().execute(gameWorld, movable);
            }
        }
    }
}
