package pl.edu.agh.ietanks.engine.testutils;

import pl.edu.agh.ietanks.engine.api.BoardDefinition;
import pl.edu.agh.ietanks.engine.api.Position;

import java.util.HashMap;

public class BoardBuilder {

    public static BoardDefinition fromASCII(String... asciiRepresentation) {
        final int height = asciiRepresentation.length;
        final int width = asciiRepresentation[0].length();

        final HashMap<String, Position> tanks = new HashMap<>();

        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                char element = asciiRepresentation[i].charAt(j);

                if (element == '.') {
                    // nothing to do
                } else if ('0' <= element && element <= '9') {
                    tanks.put(String.valueOf(element), new Position(i, j));
                } else {
                    throw new IllegalArgumentException();
                }
            }
        }

        return new FromStringBoardDefinition(width, height, tanks);
    }
}
