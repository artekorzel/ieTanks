package pl.edu.agh.ietanks.engine.testutils;

import pl.edu.agh.ietanks.engine.api.BoardDefinition;
import pl.edu.agh.ietanks.engine.api.Position;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BoardBuilder {

    public static BoardDefinition fromASCII(String... asciiRepresentation) {
        final int height = asciiRepresentation.length;
        final int width = asciiRepresentation[0].length();

        final HashMap<Integer, Position> tanks = new HashMap<>();

        for(int i=0; i<height; ++i) {
            for(int j=0; j<width; ++j) {
                char element = asciiRepresentation[i].charAt(j);

                if(element == '.') {
                    // nothing to do
                }
                else if('0' <= element && element <= '9') {
                    int tankNumber = Character.digit(element, 10);
                    tanks.put(tankNumber, new Position(i, j));
                }
                else {
                    throw new IllegalArgumentException();
                }
            }
        }

        BoardDefinition definition = new BoardDefinition() {
            @Override
            public int width() {
                return width;
            }

            @Override
            public int height() {
                return height;
            }

            @Override
            public Map<Integer, Position> initialTankPositions() {
                return Collections.unmodifiableMap(tanks);
            }
        };

        return definition;
    }
}
