package net.impactdev.impactor.scoreboards.updaters.listener;

import net.impactdev.impactor.api.Impactor;
import net.impactdev.impactor.api.text.transforming.transformers.TextTransformer;
import net.impactdev.impactor.api.utility.builders.Builder;
import net.impactdev.impactor.scoreboards.ScoreboardDisplayable;
import net.impactdev.impactor.scoreboards.updaters.ComponentResolver;
import net.kyori.adventure.text.Component;
import net.kyori.event.EventSubscription;

import java.util.function.Supplier;

public interface ListenerResolver extends ComponentResolver {

    EventSubscription subscription();

    static ListenerResolver create(ListenerConfiguration config) {
        return config.build(Impactor.instance().builders().provide(ListeningUpdaterBuilder.class));
    }

    interface ListeningUpdaterBuilder extends Builder<ListenerResolver> {

        ListeningUpdaterBuilder text(Supplier<Component> provider);

        ListeningUpdaterBuilder subscription(Subscription subscription);

        ListeningUpdaterBuilder transformer(TextTransformer transformer);

    }

    @FunctionalInterface
    interface Subscription {

        EventSubscription subscribe(ScoreboardDisplayable displayable);

    }

}
