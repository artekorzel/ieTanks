package pl.edu.agh.ietanks.engine.testutils;

import java.util.ArrayDeque;
import java.util.Queue;

import pl.edu.agh.ietanks.engine.api.Action;
import pl.edu.agh.ietanks.engine.api.GameplayBoardView;
import pl.edu.agh.ietanks.engine.api.Bot;
import pl.edu.agh.ietanks.engine.simple.actions.NoOperation;

import com.google.common.collect.Lists;

public class BotBuilder {
    public static Bot fromSequence(final Action... actions) {
        return new Bot() {
            final Queue<Action> actionQueue = new ArrayDeque<>(Lists.newArrayList(actions));

            @Override
            public Action performAction(GameplayBoardView currentBoard) {
                if(actionQueue.isEmpty()) {
                    return new NoOperation();
                }
                else {
                    return actionQueue.poll();
                }
            }
        };
    }
}
