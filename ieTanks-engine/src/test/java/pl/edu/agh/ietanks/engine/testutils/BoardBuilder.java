package pl.edu.agh.ietanks.engine.testutils;

import com.google.common.collect.Lists;
import pl.edu.agh.ietanks.engine.api.BoardDefinition;
import pl.edu.agh.ietanks.engine.api.Position;

import java.util.List;

public class BoardBuilder {

    public static BoardDefinition fromASCII(String... asciiRepresentation) {
        final int height = asciiRepresentation.length;
        final int width = asciiRepresentation[0].length();

        final List<Position> startingPositions = Lists.newArrayList();
        final List<Position> obstaclesPositions = Lists.newArrayList();

        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                char element = asciiRepresentation[i].charAt(j);

                if (element == '.') {
                    // nothing to do
                } else if (element == 'x') {
                    startingPositions.add(Position.topLeft().toDown(i).toRight(j));
                } else if (element == '#') {
                    obstaclesPositions.add(Position.topLeft().toDown(i).toRight(j));
                } else {
                    throw new IllegalArgumentException("Got unexpected character: " + element);
                }
            }
        }

        return new BoardDefinition(width, height, startingPositions, obstaclesPositions);
    }
}
