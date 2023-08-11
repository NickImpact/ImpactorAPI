package net.impactdev.impactor.scoreboards.updaters.listener;

import net.kyori.event.EventSubscription;

public interface BusManager {

    EventSubscription subscription();



    default void shutdown() {
        this.subscription().unsubscribe();
    }

}
