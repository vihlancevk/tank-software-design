package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LevelModelTest {
    private static final int LEVEL_WIDTH = 10;
    private static final int LEVEL_HEIGHT = 8;

    private LevelModel levelModel;

    @BeforeEach
    void setUp() {
        levelModel = new LevelModel(LEVEL_WIDTH, LEVEL_HEIGHT);
    }

    @Test
    void testIsFree_WhenNoObstacles_ReturnsTrue() {
        // Arrange

        // Act

        // Assert
        assertTrue(levelModel.isAvailableForMove(new GridPoint2(1, 1)));
        assertTrue(levelModel.isAvailableForMove(new GridPoint2(5, 5)));
    }

    @Test
    void testIsFree_WhenObstacleAtSamePosition_ReturnsFalse() {
        // Arrange
        ObstacleModel obstacleModel = new ObstacleModel(new GridPoint2(1, 2));

        // Act
        levelModel.addObstacleModel(obstacleModel);

        // Assert
        assertFalse(levelModel.isAvailableForMove(new GridPoint2(1, 2)));
    }

    @Test
    void testIsFree_WhenObstacleAtDifferentPosition_ReturnsTrue() {
        // Arrange
        ObstacleModel obstacleModel = mock(ObstacleModel.class);
        when(obstacleModel.getPosition()).thenReturn(new GridPoint2(2, 3));

        // Act
        levelModel.addObstacleModel(obstacleModel);

        // Assert
        assertTrue(levelModel.isAvailableForMove(new GridPoint2(1, 1)));
    }

    @Test
    void testIsFree_WithMultipleObstacles_CorrectlyDetectsOccupiedAndFree() {
        // Arrange
        ObstacleModel obstacleModel1 = new ObstacleModel(new GridPoint2(1, 1));
        ObstacleModel obstacleModel2 = new ObstacleModel(new GridPoint2(2, 2));

        // Act
        levelModel.addObstacleModel(obstacleModel1);
        levelModel.addObstacleModel(obstacleModel2);

        // Assert
        assertFalse(levelModel.isAvailableForMove(new GridPoint2(1, 1)));
        assertFalse(levelModel.isAvailableForMove(new GridPoint2(2, 2)));
        assertTrue(levelModel.isAvailableForMove(new GridPoint2(3, 3)));
    }

    @Test
    void testIsFree_WhenPositionOutsideBounds_ReturnsFalse() {
        // Arrange

        // Act

        // Assert
        assertFalse(levelModel.isAvailableForMove(new GridPoint2(-1, 0)));
        assertFalse(levelModel.isAvailableForMove(new GridPoint2(0, -1)));
        assertFalse(levelModel.isAvailableForMove(new GridPoint2(10, 0)));
        assertFalse(levelModel.isAvailableForMove(new GridPoint2(0, 8)));
    }

    @Test
    void testIsFree_WhenEntityDestinationMatchesPosition_ReturnsFalse() {
        // Arrange
        PlayerModel playerModel = new PlayerModel(new GridPoint2(5, 5), 1.0f, 100);
        levelModel.setPlayerModel(playerModel);

        // Act

        // Assert
        assertFalse(levelModel.isAvailableForMove(new GridPoint2(5, 5)));
    }

    @Test
    void testAddEntityModel_WhenNull_ThrowsException() {
        // Arrange

        // Act & Assert
        assertThrows(NullPointerException.class, () -> levelModel.addObstacleModel(null));
    }

    @Test
    void testIsFree_OnBorderPositionsInsideBounds_ReturnsTrue() {
        // Arrange

        // Act

        // Assert
        assertTrue(levelModel.isAvailableForMove(new GridPoint2(1, 1)));
        assertTrue(levelModel.isAvailableForMove(new GridPoint2(9, 7)));
    }

    @Test
    void testIsFree_WhenEntityAtCornerBlocksOnlyThatCell() {
        // Arrange
        PlayerModel playerModel = new PlayerModel(new GridPoint2(0, 0), 1.0f, 100);
        levelModel.setPlayerModel(playerModel);

        // Act

        // Assert
        assertFalse(levelModel.isAvailableForMove(new GridPoint2(0, 0)));
        assertTrue(levelModel.isAvailableForMove(new GridPoint2(1, 0)));
        assertTrue(levelModel.isAvailableForMove(new GridPoint2(1, 1)));
    }
}
