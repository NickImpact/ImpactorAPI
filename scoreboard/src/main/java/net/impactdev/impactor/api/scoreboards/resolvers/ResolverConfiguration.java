package net.impactdev.impactor.api.scoreboards.resolvers;

import net.impactdev.impactor.api.scoreboards.resolvers.updaters.ComponentProvider;
import net.impactdev.impactor.api.utility.builders.Builder;

public interface ResolverConfiguration {

    ComponentProvider provider();

    interface ConfigurationBuilder<T, B extends ConfigurationBuilder<T, B>> extends Builder<T> {

        B provider(ComponentProvider provider);

    }

}
