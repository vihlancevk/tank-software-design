package ru.mipt.bit.platformer.controller;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.api.GameWorld;
import ru.mipt.bit.platformer.app.InputHandler;
import ru.mipt.bit.platformer.command.Command;
import ru.mipt.bit.platformer.model.LevelModel;
import ru.mipt.bit.platformer.util.GdxGameUtils;
import ru.mipt.bit.platformer.view.LevelView;

import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class LevelController implements GameWorld {
    private final Batch batch;
    private final InputHandler inputHandler;
    private final LevelModel levelModel;
    private final LevelView levelView;

    private PlayerController playerController = null;
    private final Set<ObstacleController> obstacleControllers = new HashSet<>();
    private final Set<PlayerController> botControllers = new HashSet<>();
    private final Set<BulletController> bulletControllers = new HashSet<>();

    private LevelController(Batch batch, InputHandler inputHandler, LevelModel levelModel, LevelView levelView) {
        this.batch = batch;
        this.inputHandler = inputHandler;
        this.levelModel = levelModel;
        this.levelView = levelView;
    }

    public static LevelController create(
            int width,
            int height,
            TiledMap tiledMap,
            Batch batch,
            InputHandler inputHandler
    ) {
        LevelModel model = new LevelModel(width, height);
        LevelView view = new LevelView(tiledMap, GdxGameUtils.createSingleLayerMapRenderer(tiledMap, batch));
        return new LevelController(batch, inputHandler, model, view);
    }

    @Override
    public boolean isAvailableForMove(int x, int y) {
        return levelModel.isAvailableForMove(new GridPoint2(x, y));
    }

    public void setPlayerController(PlayerController playerController) {
        place(playerController);
        this.playerController = playerController;
        levelModel.setPlayerModel(playerController.getEntityModel());
    }

    public void addObstacleController(ObstacleController obstacleController) {
        place(obstacleController);
        obstacleControllers.add(obstacleController);
        levelModel.addObstacleModel(obstacleController.getEntityModel());
    }

    public void addBotController(PlayerController botController) {
        place(botController);
        botControllers.add(botController);
        levelModel.addBotModel(botController.getEntityModel());
    }

    @Override
    public void addBulletController(BulletController bulletController) {
        place(bulletController);
        bulletControllers.add(bulletController);
        levelModel.addBulletModel(bulletController.getEntityModel());
    }

    public void handleInput() {
        Queue<Command> commands = inputHandler.handleInput(playerController, botControllers);
        while (!commands.isEmpty()) {
            Command command = commands.poll();
            command.execute(this);
        }
    }

    public void update(float delta) {
        updatePlayerController(delta);
        updateBotControllers(delta);
        updateBulletControllers(delta);
    }

    public void render() {
        levelView.render();
        batch.begin();
        if (playerController != null) {
            playerController.render(batch);
        }
        obstacleControllers.forEach(obstacleController -> obstacleController.render(batch));
        botControllers.forEach(botController -> botController.render(batch));
        bulletControllers.forEach(bulletController -> bulletController.render(batch));
        batch.end();
    }

    public void dispose() {
        levelView.dispose();
        if (playerController != null) {
            playerController.dispose();
        }
        obstacleControllers.forEach(EntityController::dispose);
        botControllers.forEach(EntityController::dispose);
        bulletControllers.forEach(EntityController::dispose);
    }

    private void place(EntityController<?, ?> entityController) {
        GdxGameUtils.moveRectangleAtTileCenter(
                GdxGameUtils.getSingleLayer(levelView.getTiledMap()),
                entityController.viewable.getBounds(),
                entityController.getEntityModel().getPosition()
        );
    }

    private void updatePlayerController(float delta) {
        if (playerController == null) {
            return;
        }

        if (playerController.getEntityModel().getCurHealth() <= 0) {
            levelModel.unsetPlayerModel();
            playerController.dispose();
            playerController = null;
        } else {
            playerController.update(delta);
        }
    }

    private void updateBotControllers(float delta) {
        Set<PlayerController> botControllersForRemove = new HashSet<>();
        botControllers.stream()
                .filter(botController -> botController.getEntityModel().getCurHealth() <= 0)
                .forEach(botControllersForRemove::add);

        for (PlayerController botControllerForRemove : botControllersForRemove) {
            levelModel.removeBotControllerModel(botControllerForRemove.getEntityModel());
            botControllers.remove(botControllerForRemove);
            botControllerForRemove.dispose();
        }

        botControllers.forEach(botController -> botController.update(delta));
    }

    private void updateBulletControllers(float delta) {
        Set<BulletController> bulletControllersForRemove = new HashSet<>();
        for (BulletController bulletController : bulletControllers) {
            if (bulletController.getEntityModel().isSkipProcessingOnStart()) {
                continue;
            }

            GridPoint2 bulletPosition = bulletController.getEntityModel().getPosition();
            int damage = bulletController.getEntityModel().getDamage();
            if (!levelModel.isInsideBounds(bulletPosition)) {
                bulletControllersForRemove.add(bulletController);
                continue;
            }

            if (playerController != null && playerController.getEntityModel().getPosition().equals(bulletPosition)) {
                playerController.getEntityModel().decreaseHealth(damage);
                bulletControllersForRemove.add(bulletController);
            }

            obstacleControllers.stream()
                    .filter(obstacleController -> obstacleController.getEntityModel().getPosition().equals(bulletPosition))
                    .findFirst()
                    .ifPresent(obstacleController -> bulletControllersForRemove.add(bulletController));

            botControllers.stream()
                    .filter(botController -> botController.getEntityModel().getPosition().equals(bulletPosition))
                    .findFirst()
                    .ifPresent(botController -> {
                        botController.getEntityModel().decreaseHealth(damage);
                        bulletControllersForRemove.add(bulletController);
                    });

            bulletControllers.stream()
                    .filter(bc -> !bc.equals(bulletController))
                    .filter(bc -> bc.getEntityModel().getPosition().equals(bulletPosition))
                    .findFirst()
                    .ifPresent(bc -> bulletControllersForRemove.add(bulletController));
        }

        for (BulletController bulletControllerForRemove : bulletControllersForRemove) {
            levelModel.removeBulletModel(bulletControllerForRemove.getEntityModel());
            bulletControllers.remove(bulletControllerForRemove);
            bulletControllerForRemove.dispose();
        }

        bulletControllers.forEach(bulletController -> bulletController.update(delta));
    }
}
