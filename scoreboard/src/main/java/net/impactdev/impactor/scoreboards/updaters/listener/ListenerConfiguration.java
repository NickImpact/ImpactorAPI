package net.impactdev.impactor.scoreboards.updaters.listener;

@FunctionalInterface
public interface ListenerConfiguration {

    ListenerResolver build(ListenerResolver.ListeningUpdaterBuilder builder);

}
