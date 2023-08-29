package net.impactdev.impactor.api.scheduler;

import net.kyori.adventure.key.Key;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 *
 *
 * @since 5.2.0
 */
public interface Scheduler {

    Key SYNCHRONOUS = Key.key("impactor", "sync");
    Key ASYNCHRONOUS = Key.key("impactor", "async");

    Key key();

    Executor executor();

    void publish(Runnable action);

    SchedulerTask delayed(Runnable action, Ticks ticks);

    SchedulerTask delayed(Runnable action, long delay, TimeUnit unit);

    SchedulerTask repeating(Runnable action, Ticks tick);

    SchedulerTask repeating(Runnable action, long interval, TimeUnit unit);

    SchedulerTask delayedAndRepeating(Runnable action, Ticks delay, Ticks interval);

    SchedulerTask delayedAndRepeating(Runnable action, long delay, long interval, TimeUnit unit);

}
