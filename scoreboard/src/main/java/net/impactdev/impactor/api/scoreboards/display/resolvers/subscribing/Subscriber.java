package net.impactdev.impactor.api.scoreboards.display.resolvers.subscribing;

import net.kyori.event.EventSubscription;

import java.util.function.Consumer;

public interface Subscriber<T> {

    void validateEventType(Class<T> event) throws IllegalArgumentException;

    EventSubscription subscribe(Class<T> event, Consumer<T> listener);

}
