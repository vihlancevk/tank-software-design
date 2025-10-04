package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.Direction;
import ru.mipt.bit.platformer.util.TileMovement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class PlayerModelTest {
    private LevelModel levelModel;

    private GridPoint2 startPosition;
    private Rectangle bounds;
    private TileMovement movement;
    private PlayerModel playerModel;

    @BeforeEach
    void setUp() {
        levelModel = mock(LevelModel.class);

        startPosition = new GridPoint2(0, 0);
        bounds = new Rectangle(0, 0, 1, 1);
        movement = mock(TileMovement.class);
        playerModel = new PlayerModel(bounds, startPosition, movement, 1f);
    }

    @Test
    void testInitialState() {
        // Assert

        // Act

        // Arrange
        assertEquals(0f, playerModel.getRotation(), 0.0001);
        assertEquals(1f, playerModel.getProgress(), 0.0001);
        assertEquals(startPosition, playerModel.getDestination());
    }

    @Test
    void testMove_WhenCellIsFree_UpdatesDestinationAndProgress() {
        // Arrange
        when(levelModel.isFree(new GridPoint2(0, 1))).thenReturn(true);

        // Act
        playerModel.move(levelModel, Direction.UP);

        // Assert
        GridPoint2 destination = playerModel.getDestination();
        assertEquals(new GridPoint2(0, 1), destination);
        assertEquals(0f, playerModel.getProgress(), 0.0001);
        assertEquals(Direction.UP.rotation, playerModel.getRotation(), 0.0001);
    }

    @Test
    void testMove_WhenCellIsBlocked_DoesNotChangeDestination() {
        // Arrange
        when(levelModel.isFree(new GridPoint2(0, 1))).thenReturn(false);

        // Act
        playerModel.move(levelModel, Direction.UP);

        // Assert
        assertEquals(1f, playerModel.getProgress(), 0.0001);
        assertEquals(new GridPoint2(0, 0), playerModel.getDestination());
        assertEquals(Direction.UP.rotation, playerModel.getRotation(), 0.0001);
    }

    @Test
    void testUpdate_CallsMovementAndIncreasesProgress() {
        // Arrange
        when(levelModel.isFree(any())).thenReturn(true);
        playerModel.move(levelModel, Direction.RIGHT);

        // Act
        float oldProgress = playerModel.getProgress();
        playerModel.update(0.5f);

        // Assert
        verify(movement).moveRectangleBetweenTileCenters(eq(bounds), any(), any(), anyFloat());
        float newProgress = playerModel.getProgress();
        assertTrue(newProgress > oldProgress || newProgress == 1f);
    }

    @Test
    void testUpdate_WhenProgressReachesOne_PositionUpdated() {
        // Arrange
        when(levelModel.isFree(any())).thenReturn(true);
        playerModel.move(levelModel, Direction.UP);

        // Act
        playerModel.update(1f);

        // Assert
        assertEquals(playerModel.getDestination(), playerModel.getPosition());
    }
}
