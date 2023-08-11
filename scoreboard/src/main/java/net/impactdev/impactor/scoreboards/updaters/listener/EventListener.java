package net.impactdev.impactor.scoreboards.updaters.listener;

@FunctionalInterface
public interface EventListener<L> {

    boolean process(L event);

}
