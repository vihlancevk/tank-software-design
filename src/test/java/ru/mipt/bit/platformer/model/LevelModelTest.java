package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LevelModelTest {
    private LevelModel levelModel;

    @BeforeEach
    void setUp() {
        levelModel = new LevelModel(10, 8);
    }

    @Test
    void testIsFree_WhenNoObstacles_ReturnsTrue() {
        // Arrange

        // Act

        // Assert
        assertTrue(levelModel.isAvailable(new GridPoint2(0, 0)));
        assertTrue(levelModel.isAvailable(new GridPoint2(5, 5)));
    }

    @Test
    void testIsFree_WhenObstacleAtSamePosition_ReturnsFalse() {
        // Arrange
        ObstacleModel obstacleModel = mock(ObstacleModel.class);
        when(obstacleModel.getPosition()).thenReturn(new GridPoint2(1, 2));
        when(obstacleModel.getDestination()).thenReturn(new GridPoint2(1, 2));

        // Act
        levelModel.addEntityModel(obstacleModel);

        // Assert
        assertFalse(levelModel.isAvailable(new GridPoint2(1, 2)));
    }

    @Test
    void testIsFree_WhenObstacleAtDifferentPosition_ReturnsTrue() {
        // Arrange
        EntityModel obstacleModel = mock(ObstacleModel.class);
        when(obstacleModel.getPosition()).thenReturn(new GridPoint2(2, 3));
        when(obstacleModel.getDestination()).thenReturn(new GridPoint2(2, 3));

        // Act
        levelModel.addEntityModel(obstacleModel);

        // Assert
        assertTrue(levelModel.isAvailable(new GridPoint2(0, 0)));
    }

    @Test
    void testIsFree_WithMultipleObstacles_CorrectlyDetectsOccupiedAndFree() {
        // Arrange
        EntityModel obstacleModel1 = mock(ObstacleModel.class);
        when(obstacleModel1.getPosition()).thenReturn(new GridPoint2(1, 1));
        when(obstacleModel1.getDestination()).thenReturn(new GridPoint2(1, 1));

        EntityModel obstacleModel2 = mock(ObstacleModel.class);
        when(obstacleModel2.getPosition()).thenReturn(new GridPoint2(2, 2));
        when(obstacleModel2.getDestination()).thenReturn(new GridPoint2(2, 2));

        // Act
        levelModel.addEntityModel(obstacleModel1);
        levelModel.addEntityModel(obstacleModel2);

        // Assert
        assertFalse(levelModel.isAvailable(new GridPoint2(1, 1)));
        assertFalse(levelModel.isAvailable(new GridPoint2(2, 2)));
        assertTrue(levelModel.isAvailable(new GridPoint2(3, 3)));
    }

    @Test
    void testIsFree_WhenPositionOutsideBounds_ReturnsFalse() {
        // Arrange

        // Act

        // Assert
        assertFalse(levelModel.isAvailable(new GridPoint2(-1, 0)));
        assertFalse(levelModel.isAvailable(new GridPoint2(0, -1)));
        assertFalse(levelModel.isAvailable(new GridPoint2(10, 0)));
        assertFalse(levelModel.isAvailable(new GridPoint2(0, 8)));
    }

    @Test
    void testIsFree_WhenEntityDestinationMatchesPosition_ReturnsFalse() {
        // Arrange
        EntityModel entity = mock(EntityModel.class);
        when(entity.getPosition()).thenReturn(new GridPoint2(1, 1));
        when(entity.getDestination()).thenReturn(new GridPoint2(5, 5));

        // Act
        levelModel.addEntityModel(entity);

        // Assert
        assertFalse(levelModel.isAvailable(new GridPoint2(5, 5)));
    }

    @Test
    void testAddEntityModel_WhenNull_ThrowsException() {
        // Arrange

        // Act & Assert
        assertThrows(NullPointerException.class, () -> levelModel.addEntityModel(null));
    }

    @Test
    void testIsFree_OnBorderPositionsInsideBounds_ReturnsTrue() {
        // Arrange

        // Act

        // Assert
        assertTrue(levelModel.isAvailable(new GridPoint2(0, 0)));
        assertTrue(levelModel.isAvailable(new GridPoint2(9, 7)));
    }

    @Test
    void testIsFree_WhenEntityAtCornerBlocksOnlyThatCell() {
        // Arrange
        EntityModel entity = mock(EntityModel.class);
        when(entity.getPosition()).thenReturn(new GridPoint2(0, 0));
        when(entity.getDestination()).thenReturn(new GridPoint2(0, 0));

        // Act
        levelModel.addEntityModel(entity);

        // Assert
        assertFalse(levelModel.isAvailable(new GridPoint2(0, 0)));
        assertTrue(levelModel.isAvailable(new GridPoint2(1, 0)));
        assertTrue(levelModel.isAvailable(new GridPoint2(0, 1)));
    }
}
