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

package net.impactdev.impactor.api.scheduler.v2;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import net.impactdev.impactor.api.logging.PluginLogger;
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

    public static void shutdown(PluginLogger logger) {
        schedulers.forEach((key, scheduler) -> {
            logger.info("Shutting down scheduler: " + key);
            scheduler.shutdown();
        });
    }
}
