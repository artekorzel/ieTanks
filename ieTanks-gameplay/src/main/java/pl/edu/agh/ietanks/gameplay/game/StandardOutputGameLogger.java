package pl.edu.agh.ietanks.gameplay.game;

import pl.edu.agh.ietanks.engine.api.GameplayBoardView;
import pl.edu.agh.ietanks.engine.api.events.Event;
import pl.edu.agh.ietanks.engine.api.events.RoundResults;
import pl.edu.agh.ietanks.gameplay.game.innerapi.GameLogger;

public class StandardOutputGameLogger implements GameLogger{
    private int roundIdx;

    @Override
    public void startGame() {
        this.roundIdx = 0;
        System.out.println("\n--------GAME STARTED--------\n");
    }

    @Override
    public void nextRoundResults(RoundResults results, GameplayBoardView gameplayBoardView) {
        StringBuilder roundEvents = new StringBuilder();

        roundEvents
                    .append("ROUND ")
                    .append(roundIdx++)
                    .append(" :\n")
                    ;

        if(results.isGameFinished()){
            roundEvents.append("--------GAME FINISHED--------\n");
        }else{
            for(Event e : results.getRoundEvents()){
                roundEvents
                            .append("\t")
                            .append(e)
                            .append("\n")
                            ;
            }
        }

        System.out.println(roundEvents.toString());
        System.out.println(gameplayBoardView.toString());
    }
}
