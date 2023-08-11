package net.impactdev.impactor.scoreboards.updaters.listener;

import net.impactdev.impactor.api.utility.builders.Builder;
import net.impactdev.impactor.scoreboards.updaters.ComponentUpdater;
import net.impactdev.impactor.scoreboards.utility.TextTemplate;
import net.kyori.event.EventSubscription;

public interface ListeningUpdater extends ComponentUpdater {

    EventSubscription subscription();

    @FunctionalInterface
    interface Configuration {

        ListeningUpdater configure(ListeningUpdaterBuilder builder);

    }

    interface ListeningUpdaterBuilder extends Builder<ListeningUpdater> {

        ListeningUpdaterBuilder template(TextTemplate template);

        <B> ListeningUpdaterBuilder configure(B bus, Class<?> event);

    }

    @FunctionalInterface
    interface BusConfiguration<B> {

        EventSubscription configure(B bus);

    }

}
