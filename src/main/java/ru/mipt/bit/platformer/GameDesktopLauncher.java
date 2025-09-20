package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GameDesktopLauncher implements ApplicationListener {
    private static final float MOVEMENT_SPEED = 0.4f;

    private EntityFactory entityFactory;
    private PlayerInputController playerInputController;

    private Batch batch;
    private Level level;
    private Player player;

    @Override
    public void create() {
        batch = new SpriteBatch();

        TiledMap map = new TmxMapLoader().load("level.tmx");

        entityFactory = new EntityFactory(map);
        playerInputController = new PlayerInputController();

        level = createLevel(map);
        level.addObstacle(entityFactory.createObstacle("images/green_tree.png", 1, 3));

        player = entityFactory.createPlayer("images/tank_blue.png", 1, 1, MOVEMENT_SPEED);
    }

    @Override
    public void render() {
        clearScreen();

        float delta = getTimePassedSinceLastRender();

        playerInputController.getDirection().ifPresent(dir -> player.move(dir, level));

        player.update(delta);

        level.render(batch);
        batch.begin();
        player.render(batch);
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
        level.dispose();
        player.dispose();
    }

    private Level createLevel(TiledMap map) {
        MapRenderer renderer = createSingleLayerMapRenderer(map, batch);
        return new Level(map, renderer);
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }

    private float getTimePassedSinceLastRender() {
        return Gdx.graphics.getDeltaTime();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
