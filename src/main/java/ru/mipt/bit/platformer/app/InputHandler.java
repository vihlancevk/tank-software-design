package ru.mipt.bit.platformer.app;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.api.GameWorld;
import ru.mipt.bit.platformer.api.Movable;
import ru.mipt.bit.platformer.command.*;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.badlogic.gdx.Input.Keys.*;

public class InputHandler {
    private static final List<CommandFactory> BOT_ACTIONS = List.of(
            MoveUpCommand::new,
            MoveDownCommand::new,
            MoveLeftCommand::new,
            MoveRightCommand::new
    );

    public void handleBotInput(GameWorld gameWorld, Movable movable) {
        CommandFactory factory = BOT_ACTIONS.get(ThreadLocalRandom.current().nextInt(BOT_ACTIONS.size()));
        factory.create(movable).execute(gameWorld);
    }

    public void handlePlayerInput(GameWorld gameWorld, Movable movable) {
        Command command = null;

        if (isPressed(W, UP)) {
            command = new MoveUpCommand(movable);
        } else if (isPressed(S, DOWN)) {
            command = new MoveDownCommand(movable);
        } else if (isPressed(A, LEFT)) {
            command = new MoveLeftCommand(movable);
        } else if (isPressed(D, RIGHT)) {
            command = new MoveRightCommand(movable);
        }

        if (command != null) {
            command.execute(gameWorld);
        }
    }

    private boolean isPressed(int key1, int key2) {
        return Gdx.input.isKeyPressed(key1) || Gdx.input.isKeyPressed(key2);
    }

    @FunctionalInterface
    private interface CommandFactory {
        Command create(Movable movable);
    }
}
