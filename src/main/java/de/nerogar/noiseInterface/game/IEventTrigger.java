package de.nerogar.noiseInterface.game;

import de.nerogar.noiseInterface.event.IEvent;

public interface IEventTrigger<T extends IEvent> {

	void trigger(T event);
}
