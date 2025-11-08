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
    private static final float BOT_MOVE_COOLDOWN = 0.125f;
    private static final List<CommandFactory> BOT_ACTIONS = List.of(
            MoveUpCommand::new,
            MoveDownCommand::new,
            MoveLeftCommand::new,
            MoveRightCommand::new
    );

    public void handleInput(
            GameWorld gameWorld,
            List<Bot> bots,
            PlayerController playerController,
            float delta) {
        handleLevelInput(gameWorld, bots, playerController);
        bots.forEach(bot -> handleBotInput(gameWorld, bot, delta));
        handlePlayerInput(gameWorld, playerController, delta);
    }

    private void handleLevelInput(
            GameWorld gameWorld,
            List<Bot> bots,
            PlayerController player
    ) {
        if (isJustPressed(L)) {
            bots.forEach(bot -> new ToggleHealthBarCommand(bot.getController()).execute(gameWorld));
            new ToggleHealthBarCommand(player).execute(gameWorld);
        }
    }

    private void handleBotInput(GameWorld gameWorld, Bot bot, float delta) {
        bot.decreaseCooldown(delta);

        if (bot.getCooldown() <= 0.0f) {
            CommandFactory factory = BOT_ACTIONS.get(ThreadLocalRandom.current().nextInt(BOT_ACTIONS.size()));
            factory.create(bot.getController()).execute(gameWorld);

            bot.setCooldown(BOT_MOVE_COOLDOWN);
        }
    }

    private void handlePlayerInput(GameWorld gameWorld, PlayerController player, float delta) {
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
