package ru.mipt.bit.platformer.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import ru.mipt.bit.platformer.controller.EntityController;
import ru.mipt.bit.platformer.controller.LevelController;
import ru.mipt.bit.platformer.controller.ObstacleController;
import ru.mipt.bit.platformer.controller.PlayerController;

import java.util.List;
import java.util.stream.Collectors;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.createSingleLayerMapRenderer;

public class GameDesktopLauncher implements ApplicationListener {
    private static final int WIDTH_IN_TILES = 10;
    private static final int HEIGHT_IN_TILES = 8;
    private static final int N_PIXELS_IN_TILE = 128;
    private static final float MOVEMENT_SPEED = 0.4f;

    private final InputHandler inputHandler;

    private Batch batch;

    private List<ObstacleController> obstacleControllers;
    private List<Bot> bots;
    private PlayerController playerController;
    private LevelController levelController;

    public GameDesktopLauncher(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();

        TiledMap tiledMap = new TmxMapLoader().load("level.tmx");
        LevelGenerator levelGenerator = new RandomLevelGenerator(
                WIDTH_IN_TILES, HEIGHT_IN_TILES, new TiledEntityControllerFactory(tiledMap)
        );

        obstacleControllers = levelGenerator.generateObstacleControllers("images/green_tree.png");
        List<PlayerController> botControllers = levelGenerator.generateBotControllers("images/tank_blue.png", MOVEMENT_SPEED);
        bots = botControllers.stream().map(Bot::new).collect(Collectors.toList());
        playerController = levelGenerator.generatePlayerController("images/tank_blue.png", MOVEMENT_SPEED);
        levelController = LevelController.create(
                WIDTH_IN_TILES,
                HEIGHT_IN_TILES,
                obstacleControllers,
                botControllers,
                playerController,
                tiledMap,
                createSingleLayerMapRenderer(tiledMap, batch)
        );
    }

    @Override
    public void render() {
        clearScreen();
        float delta = getTimePassedSinceLastRender();

        inputHandler.handleInput(levelController, bots, playerController, delta);
        bots.forEach(bot -> bot.update(delta));
        playerController.update(delta);

        levelController.render();
        batch.begin();
        obstacleControllers.forEach(obstacleController -> obstacleController.render(batch));
        bots.forEach(bot -> bot.render(batch));
        playerController.render(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // do not react to window resizing
    }

    @Override
    public void pause() {
        // game doesn't get paused
    }

    @Override
    public void resume() {
        // game doesn't get paused
    }

    /**
     * Dispose of all the native resources
     * (classes which implement com.badlogic.gdx.utils.Disposable).
     */
    @Override
    public void dispose() {
        batch.dispose();
        obstacleControllers.forEach(EntityController::dispose);
        bots.forEach(Bot::dispose);
        playerController.dispose();
        levelController.dispose();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }

    private float getTimePassedSinceLastRender() {
        return Gdx.graphics.getDeltaTime();
    }

    public static void main(String[] args) {
        InputHandler inputHandler = new InputHandler();
        GameDesktopLauncher gameDesktopLauncher = new GameDesktopLauncher(inputHandler);

        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowedMode(WIDTH_IN_TILES * N_PIXELS_IN_TILE, HEIGHT_IN_TILES * N_PIXELS_IN_TILE);

        new Lwjgl3Application(gameDesktopLauncher, config);
    }
}
