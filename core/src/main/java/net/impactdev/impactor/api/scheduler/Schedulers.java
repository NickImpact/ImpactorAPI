package net.impactdev.impactor.api.scheduler;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;

/**
 *
 * @since 5.2.0
 */
public final class Schedulers {

    private static final Map<Key, Scheduler> schedulers = Maps.newHashMap();

    public static void register(final @NotNull Key key, final @NotNull Scheduler scheduler) {
        schedulers.put(key, scheduler);
    }

    public static Optional<Scheduler> request(final @NotNull Key key) {
        Preconditions.checkNotNull(key);
        return Optional.ofNullable(schedulers.get(key));
    }

    public static Scheduler require(final @NotNull Key key) {
        Preconditions.checkNotNull(key);
        return Preconditions.checkNotNull(schedulers.get(key));
    }
}
