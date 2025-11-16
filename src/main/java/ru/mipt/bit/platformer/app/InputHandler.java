package ru.mipt.bit.platformer.app;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.api.Movable;
import ru.mipt.bit.platformer.api.Shootable;
import ru.mipt.bit.platformer.command.*;
import ru.mipt.bit.platformer.controller.PlayerController;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static com.badlogic.gdx.Input.Keys.*;

public class InputHandler {
    private static final List<MoveCommandFactory> BOT_MOVE_ACTIONS = List.of(
            MoveUpCommand::new,
            MoveDownCommand::new,
            MoveLeftCommand::new,
            MoveRightCommand::new
    );
    private static final ShootCommandFactory BOT_SHOOT_ACTION = ShootCommand::new;

    public Queue<Command> handleInput(PlayerController playerController, Set<PlayerController> botControllers) {
        Queue<Command> commands = new ArrayDeque<>();
        handleLevelInput(commands, playerController, botControllers);
        handlePlayerInput(commands, playerController);
        botControllers.forEach(botController -> handleBotInput(commands, botController));
        return commands;
    }

    private void handleLevelInput(
            Queue<Command> commands,
            PlayerController playerController,
            Set<PlayerController> botControllers
    ) {
        if (isJustPressed(L)) {
            if (playerController != null) {
                commands.add(new ToggleHealthBarCommand(playerController));
            }
            botControllers.forEach(botController -> commands.add(new ToggleHealthBarCommand(botController)));
        }
    }

    private void handlePlayerInput(Queue<Command> commands, PlayerController playerController) {
        if (playerController == null) {
            return;
        }

        if (isJustPressed(W, UP)) {
            commands.add(new MoveUpCommand(playerController));
        } else if (isJustPressed(S, DOWN)) {
            commands.add(new MoveDownCommand(playerController));
        } else if (isJustPressed(A, LEFT)) {
            commands.add(new MoveLeftCommand(playerController));
        } else if (isJustPressed(D, RIGHT)) {
            commands.add(new MoveRightCommand(playerController));
        } else if (isJustPressed(SPACE)) {
            commands.add(new ShootCommand(playerController));
        }
    }

    private void handleBotInput(Queue<Command> commands, PlayerController botController) {
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        double random = threadLocalRandom.nextDouble();

        if (random < 0.5) {
            return;
        }

        if (random < 0.75) {
            MoveCommandFactory factory = BOT_MOVE_ACTIONS.get(threadLocalRandom.nextInt(BOT_MOVE_ACTIONS.size()));
            commands.add(factory.create(botController));
        } else {
            commands.add(BOT_SHOOT_ACTION.create(botController));
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
    private interface MoveCommandFactory {
        Command create(Movable movable);
    }

    @FunctionalInterface
    private interface ShootCommandFactory {
        Command create(Shootable shootable);
    }
}
