package ru.mipt.bit.platformer.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import ru.mipt.bit.platformer.controller.LevelController;
import ru.mipt.bit.platformer.controller.ObstacleController;
import ru.mipt.bit.platformer.controller.PlayerController;

import java.util.List;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

public class GameDesktopLauncher implements ApplicationListener {
    private static final int WIDTH_IN_TILES = 10;
    private static final int HEIGHT_IN_TILES = 8;

    private static final int N_PIXELS_IN_TILE = 128;

    private static final float PLAYER_MOVEMENT_SPEED = 2.5f;
    private static final float BOT_MOVEMENT_SPEED = 2.5f;

    private Batch batch;
    private LevelController levelController;

    @Override
    public void create() {
        TiledMap tiledMap = new TmxMapLoader().load("level.tmx");
        batch = new SpriteBatch();
        InputHandler inputHandler = new InputHandler();

        levelController = LevelController.create(WIDTH_IN_TILES, HEIGHT_IN_TILES, tiledMap, batch, inputHandler);

        LevelGenerator levelGenerator = new RandomLevelGenerator(
                WIDTH_IN_TILES, HEIGHT_IN_TILES, new TiledEntityControllerFactory(tiledMap)
        );

        PlayerController playerController =
                levelGenerator.generatePlayerController("images/tank_blue.png", PLAYER_MOVEMENT_SPEED);
        levelController.setPlayerController(playerController);

        List<ObstacleController> obstacleControllers =
                levelGenerator.generateObstacleControllers("images/green_tree.png");
        obstacleControllers.forEach(obstacleController -> levelController.addObstacleController(obstacleController));

        List<PlayerController> botControllers =
                levelGenerator.generateBotControllers("images/tank_blue.png", BOT_MOVEMENT_SPEED);
        botControllers.forEach(botController -> levelController.addBotController(botController));
    }

    @Override
    public void render() {
        clearScreen();
        float delta = getTimePassedSinceLastRender();

        levelController.handleInput();
        levelController.update(delta);
        levelController.render();
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
        levelController.dispose();
        batch.dispose();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }

    private float getTimePassedSinceLastRender() {
        return Gdx.graphics.getDeltaTime();
    }

    public static void main(String[] args) {
        GameDesktopLauncher gameDesktopLauncher = new GameDesktopLauncher();

        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowedMode(WIDTH_IN_TILES * N_PIXELS_IN_TILE, HEIGHT_IN_TILES * N_PIXELS_IN_TILE);

        new Lwjgl3Application(gameDesktopLauncher, config);
    }
}
