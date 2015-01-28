package pl.edu.agh.ietanks.engine.api.events;

/**
 * Represent event that was triggered by the game engine in response to Action request from bot.
 * Should have a notion of a value object: be immutable, implement equals() etc.
 */
public interface Event {
    public EventGroup getEventGroup();
}
