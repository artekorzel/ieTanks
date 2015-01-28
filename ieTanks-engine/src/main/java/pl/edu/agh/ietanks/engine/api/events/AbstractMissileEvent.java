package pl.edu.agh.ietanks.engine.api.events;

import pl.edu.agh.ietanks.engine.api.Direction;
import pl.edu.agh.ietanks.engine.api.Missile;
import pl.edu.agh.ietanks.engine.api.Position;

public abstract class AbstractMissileEvent implements Event {

    protected final int id;
    protected final String tankId;
    protected final Direction direction;
    protected final Position position;
    protected final int speed;
    private final MissileAction action;

    public AbstractMissileEvent(MissileAction action, int id, String tankId, Position position, Direction direction, int speed) {
        this.id = id;
        this.tankId = tankId;
        this.direction = direction;
        this.speed = speed;
        this.position = position;
        this.action = action;
    }

    @Override
    public EventGroup getEventGroup() {
        return EventGroup.MISSILES;
    }

    public int id() {
        return id;
    }

    public String tankId() {
        return tankId;
    }

    public Position position() {
        return position;
    }

    public Direction direction() {
        return direction;
    }

    public int speed() {
        return speed;
    }

    public MissileAction getAction(){
        return action;
    }

    public enum MissileAction{
        CREATED("created"),
        MOVED("moved"),
        DESTROYED("destroyed"),
        ;

        private String actionName;
        private MissileAction(String name){
            this.actionName = name;
        }

        public String getActionName(){
            return actionName;
        }
    }
}
