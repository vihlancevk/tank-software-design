package ru.mipt.bit.platformer.api;

public interface Healthable {
    default boolean isHealthVisible() {
        return false;
    }

    default void setHealthVisible(boolean healthVisible) {
    }
}
