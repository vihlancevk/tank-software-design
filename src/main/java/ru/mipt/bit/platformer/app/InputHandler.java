package ru.mipt.bit.platformer.app;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.api.GameWorld;
import ru.mipt.bit.platformer.api.Movable;
import ru.mipt.bit.platformer.command.*;
import ru.mipt.bit.platformer.controller.PlayerController;

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

    public void handleInput(
            GameWorld gameWorld,
            List<PlayerController> botControllers,
            PlayerController playerController) {
        handleLevelInput(gameWorld, botControllers, playerController);
        botControllers.forEach(botController -> handleBotInput(gameWorld, botController));
        handlePlayerInput(gameWorld, playerController);
    }

    private void handleLevelInput(
            GameWorld gameWorld,
            List<PlayerController> bots,
            PlayerController player
    ) {
        if (isJustPressed(L)) {
            bots.forEach(bot -> new ToggleHealthBarCommand(bot).execute(gameWorld));
            new ToggleHealthBarCommand(player).execute(gameWorld);
        }
    }

    private void handleBotInput(GameWorld gameWorld, PlayerController bot) {
        CommandFactory factory = BOT_ACTIONS.get(ThreadLocalRandom.current().nextInt(BOT_ACTIONS.size()));
        factory.create(bot).execute(gameWorld);
    }

    private void handlePlayerInput(GameWorld gameWorld, PlayerController player) {
        Command command = null;

        if (isJustPressed(W, UP)) {
            command = new MoveUpCommand(player);
        } else if (isJustPressed(S, DOWN)) {
            command = new MoveDownCommand(player);
        } else if (isJustPressed(A, LEFT)) {
            command = new MoveLeftCommand(player);
        } else if (isJustPressed(D, RIGHT)) {
            command = new MoveRightCommand(player);
        }

        if (command != null) {
            command.execute(gameWorld);
        }
    }

    private boolean isJustPressed(int... keys) {
        for (int key : keys) {
            if (Gdx.input.isKeyJustPressed(key)) {
                return true;
            }
        }
        return false;
    }

    @FunctionalInterface
    private interface CommandFactory {
        Command create(Movable movable);
    }
}
