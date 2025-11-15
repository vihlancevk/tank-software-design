package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.api.GameWorld;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerModelTest {
    private GameWorld gameWorld;

    private GridPoint2 startPosition;
    private PlayerModel playerModel;

    @BeforeEach
    void setUp() {
        gameWorld = mock(GameWorld.class);

        startPosition = new GridPoint2(0, 0);
        playerModel = new PlayerModel(startPosition, 1.0f);
    }

    @Test
    void testInitialState() {
        // Arrange

        // Act

        // Assert
        assertEquals(0f, playerModel.getRotation(), 0.0001);
        assertEquals(1f, playerModel.getProgress(), 0.0001);
        assertEquals(startPosition, playerModel.getDestination());
    }

    @Test
    void testMove_WhenCellIsFree_UpdatesDestinationAndProgress() {
        // Arrange
        when(gameWorld.isAvailable(0, 1)).thenReturn(true);

        // Act
        playerModel.move(gameWorld, Direction.UP);

        // Assert
        GridPoint2 destination = playerModel.getDestination();
        assertEquals(new GridPoint2(0, 1), destination);
        assertEquals(0f, playerModel.getProgress(), 0.0001);
        assertEquals(Direction.UP.rotation, playerModel.getRotation(), 0.0001);
    }

    @Test
    void testMove_WhenCellIsBlocked_DoesNotChangeDestination() {
        // Arrange
        when(gameWorld.isAvailable(0, 1)).thenReturn(false);

        // Act
        playerModel.move(gameWorld, Direction.UP);

        // Assert
        assertEquals(1f, playerModel.getProgress(), 0.0001);
        assertEquals(new GridPoint2(0, 0), playerModel.getDestination());
        assertEquals(Direction.UP.rotation, playerModel.getRotation(), 0.0001);
    }

    @Test
    void testUpdate_CallsMovementAndIncreasesProgress() {
        // Arrange
        when(gameWorld.isAvailable(anyInt(), anyInt())).thenReturn(true);
        playerModel.move(gameWorld, Direction.RIGHT);

        // Act
        float oldProgress = playerModel.getProgress();
        playerModel.update(0.5f);

        // Assert
        float newProgress = playerModel.getProgress();
        assertTrue(newProgress > oldProgress || newProgress == 1f);
    }

    @Test
    void testUpdate_WhenProgressReachesOne_PositionUpdated() {
        // Arrange
        when(gameWorld.isAvailable(anyInt(), anyInt())).thenReturn(true);
        playerModel.move(gameWorld, Direction.UP);

        // Act
        playerModel.update(1f);

        // Assert
        assertEquals(playerModel.getDestination(), playerModel.getPosition());
    }

    @Test
    void testMove_DoesNothing_WhenProgressNotComplete() {
        // Arrange
        when(gameWorld.isAvailable(anyInt(), anyInt())).thenReturn(true);
        playerModel.move(gameWorld, Direction.RIGHT);

        // Act
        playerModel.move(gameWorld, Direction.UP);

        // Assert
        assertEquals(new GridPoint2(1, 0), playerModel.getDestination());
        assertEquals(0f, playerModel.getProgress(), 0.0001);
    }

    @Test
    void testMove_UpdatesRotationEvenWhenBlocked() {
        // Arrange
        when(gameWorld.isAvailable(1, 0)).thenReturn(false);

        // Act
        playerModel.move(gameWorld, Direction.RIGHT);

        // Assert
        assertEquals(Direction.RIGHT.rotation, playerModel.getRotation(), 0.0001);
    }

    @Test
    void testUpdate_WhenProgressLessThanOne_DoesNotChangePosition() {
        // Arrange
        when(gameWorld.isAvailable(anyInt(), anyInt())).thenReturn(true);
        playerModel.move(gameWorld, Direction.UP);

        // Act
        playerModel.update(0.1f);

        // Assert
        assertNotEquals(playerModel.getDestination(), playerModel.getPosition());
    }
}
