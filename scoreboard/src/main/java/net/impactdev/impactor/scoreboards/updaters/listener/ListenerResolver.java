package net.impactdev.impactor.scoreboards.updaters.listener;

import net.impactdev.impactor.api.Impactor;
import net.impactdev.impactor.api.utility.builders.Builder;
import net.impactdev.impactor.scoreboards.ScoreboardDisplayable;
import net.impactdev.impactor.scoreboards.updaters.ComponentResolver;
import net.kyori.event.EventSubscription;

public interface ListenerResolver extends ComponentResolver {

    EventSubscription subscription();

    static ListenerResolver create(ListenerConfiguration config) {
        return config.build(Impactor.instance().builders().provide(ListeningUpdaterBuilder.class));
    }

    interface ListeningUpdaterBuilder extends Builder<ListenerResolver> {

        ListeningUpdaterBuilder subscription(Subscription subscription);

    }

    @FunctionalInterface
    interface Subscription {

        EventSubscription subscribe(ScoreboardDisplayable displayable);

    }

}
