package pl.edu.agh.ietanks.engine.testutils;

import pl.edu.agh.ietanks.engine.api.BoardDefinition;
import pl.edu.agh.ietanks.engine.api.Position;
import pl.edu.agh.ietanks.engine.simple.BoardState;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StateBuilder {
    public static BoardState fromASCII(String... asciiRepresentation) {
        List<String> definitionAscii = Arrays.stream(asciiRepresentation).map(
                line -> line.replaceAll("[0-9]", "x")
                        .replaceAll("\\.", "x")
        ).collect(Collectors.toList());

        BoardDefinition definition = BoardBuilder.fromASCII(definitionAscii.toArray(new String[definitionAscii.size()]));
        BoardState state = new BoardState(definition);

        final int height = asciiRepresentation.length;
        final int width = asciiRepresentation[0].length();

        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                char element = asciiRepresentation[i].charAt(j);
                final Position position = Position.topLeft().toDown(i).toRight(j);

                if ('0' <= element && element <= '9') {
                    state.placeTank(String.valueOf(element), position);
                } else if (element == '.' || element == '#') {
                    // nothing to do
                } else {
                    throw new IllegalArgumentException();
                }

            }
        }

        return state;
    }
}
