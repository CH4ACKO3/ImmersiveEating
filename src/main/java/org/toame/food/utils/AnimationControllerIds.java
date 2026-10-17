package org.toame.food.utils;

import java.util.UUID;

public final class AnimationControllerIds {
    private AnimationControllerIds() {
    }

    public static long forPlayer(UUID playerId) {
        long id = playerId.getMostSignificantBits() ^ Long.rotateLeft(playerId.getLeastSignificantBits(), 1);
        id &= Long.MAX_VALUE;
        return id == 0 ? 1 : id;
    }
}
