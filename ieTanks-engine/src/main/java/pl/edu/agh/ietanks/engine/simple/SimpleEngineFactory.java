package pl.edu.agh.ietanks.engine.simple;

import org.springframework.stereotype.Service;
import pl.edu.agh.ietanks.engine.api.*;

import java.util.List;

@Service
public class SimpleEngineFactory implements EngineFactory {

    @Override
    public Engine createEngineInstance(BoardDefinition initialBoard, List<? extends Bot> bots, GameConfig configuration) {
        return new SimpleEngine(initialBoard, bots, configuration);
    }
}
