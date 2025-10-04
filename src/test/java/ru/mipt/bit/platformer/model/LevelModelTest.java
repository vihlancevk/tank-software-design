package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LevelModelTest {
    private LevelModel levelModel;

    @BeforeEach
    void setUp() {
        levelModel = new LevelModel();
    }

    @Test
    void testIsFree_WhenNoObstacles_ReturnsTrue() {
        // Arrange

        // Act

        // Assert
        assertTrue(levelModel.isFree(new GridPoint2(0, 0)));
        assertTrue(levelModel.isFree(new GridPoint2(5, 5)));
    }

    @Test
    void testIsFree_WhenObstacleAtSamePosition_ReturnsFalse() {
        // Arrange
        ObstacleModel obstacleModel = mock(ObstacleModel.class);
        when(obstacleModel.getPosition()).thenReturn(new GridPoint2(1, 2));

        // Act
        levelModel.addObstacleModel(obstacleModel);

        // Assert
        assertFalse(levelModel.isFree(new GridPoint2(1, 2)));
    }

    @Test
    void testIsFree_WhenObstacleAtDifferentPosition_ReturnsTrue() {
        // Arrange
        ObstacleModel obstacleModel = mock(ObstacleModel.class);
        when(obstacleModel.getPosition()).thenReturn(new GridPoint2(2, 3));

        // Act
        levelModel.addObstacleModel(obstacleModel);

        // Assert
        assertTrue(levelModel.isFree(new GridPoint2(0, 0)));
    }

    @Test
    void testIsFree_WithMultipleObstacles_CorrectlyDetectsOccupiedAndFree() {
        // Arrange
        ObstacleModel obstacleModel1 = mock(ObstacleModel.class);
        ObstacleModel obstacleModel2 = mock(ObstacleModel.class);

        when(obstacleModel1.getPosition()).thenReturn(new GridPoint2(1, 1));
        when(obstacleModel2.getPosition()).thenReturn(new GridPoint2(2, 2));

        // Act
        levelModel.addObstacleModel(obstacleModel1);
        levelModel.addObstacleModel(obstacleModel2);

        // Assert
        assertFalse(levelModel.isFree(new GridPoint2(1, 1)));
        assertFalse(levelModel.isFree(new GridPoint2(2, 2)));
        assertTrue(levelModel.isFree(new GridPoint2(3, 3)));
    }
}
