package net.impactdev.impactor.scoreboards.updaters.scheduled;

@FunctionalInterface
public interface SchedulerConfiguration {

    ScheduledResolver configure(ScheduledResolver.ScheduledResolverBuilder builder);

}
