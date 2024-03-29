/*
 * This file is part of Impactor, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2018-2022 NickImpact
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 */

package net.impactdev.impactor.api.mail.filters;

import com.google.common.base.Preconditions;
import net.impactdev.impactor.api.platform.sources.PlatformSource;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

public final class MailFilters {

    static MailFilter createSenderFilter(final boolean includeConsole, final UUID... players) {
        return createSenderFilter(includeConsole, Arrays.asList(players));
    }

    static MailFilter createSenderFilter(final boolean includeConsole, final Collection<UUID> players) {
        return m -> m.source().map(players::contains).orElse(includeConsole);
    }

    static MailFilter createDateFilter(@Nullable final Instant after, @Nullable final Instant before) {
        Preconditions.checkArgument(after != null || before != null);
        final Instant iAfter = after == null ? Instant.ofEpochMilli(0) : after;
        final Instant iBefore = before == null ? Instant.now() : before;

        return m -> iAfter.isBefore(m.timestamp()) && iBefore.isAfter(m.timestamp());
    }

}
