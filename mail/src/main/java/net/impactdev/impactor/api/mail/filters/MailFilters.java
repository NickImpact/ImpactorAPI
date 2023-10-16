package net.impactdev.impactor.api.mail.filters;

import com.google.common.base.Preconditions;
import net.impactdev.impactor.api.platform.sources.PlatformSource;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

public interface MailFilters {

    default MailFilter createSenderFilter(final boolean includeConsole, final UUID... players) {
        return this.createSenderFilter(includeConsole, Arrays.asList(players));
    }

    default MailFilter createSenderFilter(final boolean includeConsole, final Collection<UUID> players) {
        return m -> {
            if(players.contains(m.sender())) {
                return true;
            }

            return m.sender().equals(PlatformSource.SERVER_UUID) && includeConsole;
        };
    }

    default MailFilter createDateFilter(@Nullable final Instant after, @Nullable final Instant before) {
        Preconditions.checkArgument(after != null || before != null);
        final Instant iAfter = after == null ? Instant.ofEpochMilli(0) : after;
        final Instant iBefore = before == null ? Instant.now() : before;

        return m -> iAfter.isBefore(m.timestamp()) && iBefore.isAfter(m.timestamp());
    }

}
