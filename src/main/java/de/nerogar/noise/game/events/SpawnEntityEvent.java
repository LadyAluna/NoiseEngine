package de.nerogar.noise.game.events;

import de.nerogar.noise.event.IEvent;
import de.nerogar.noiseInterface.game.IComponent;

public class SpawnEntityEvent implements IEvent {

	public final int          entityId;
	public final IComponent[] components;

	public SpawnEntityEvent(int entityId, IComponent[] components) {
		this.entityId = entityId;
		this.components = components;
	}
}