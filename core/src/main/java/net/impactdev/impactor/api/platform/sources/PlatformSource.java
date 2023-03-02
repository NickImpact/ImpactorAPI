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

package net.impactdev.impactor.api.platform.sources;

import net.impactdev.impactor.api.Impactor;
import net.impactdev.impactor.api.platform.audience.LocalizedAudience;
import net.impactdev.impactor.api.platform.sources.metadata.MetadataKey;
import net.impactdev.impactor.api.platform.sources.metadata.MetadataKeys;
import net.kyori.adventure.text.Component;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * Represents a {@link LocalizedAudience} which is capable of performing actions against the server.
 * Some sources may or may not exist within the world itself, such as players or command blocks.
 *
 */
public interface PlatformSource extends LocalizedAudience {

    /** A default UUID of all 0's, used to represent the console */
    UUID CONSOLE_UUID = new UUID(0, 0);

    /**
     * Creates a source which represents the server console for the running server instance.
     *
     * @return A source representing the server console
     */
    static PlatformSource console() {
        return Impactor.instance().factories().provide(Factory.class).console();
    }

    static Factory factory() {
        return Impactor.instance().factories().provide(Factory.class);
    }

    /**
     * Indicates the UUID of the source this platform instance belongs to. This field will always
     * be available, despite whether it correctly maps to a player or not.
     *
     * @return The UUID of the source
     */
    UUID uuid();

    /**
     * Specifies the type represented by this source. This is used for additional translation of
     * possible sources.
     *
     * @return The {@link SourceType} represented by this PlatformSource
     */
    SourceType type();

    /**
     * Represents the name of the source. This is meant to target the source's specific name, rather
     * than their own display name.
     *
     * @return A component representing the actual name of a source
     */
    Component name();

    /**
     * Attempts to find and return a value bound to this source based on a given metadata key. Typical API keys
     * can be found using {@link MetadataKeys}, but more
     * can be created via the {@link MetadataKey.Factory} provider.
     *
     * @param key The key to use for metadata lookup
     * @return An optionally wrapped value containing the result of the metadata key lookup, or {@link Optional#empty()}
     * if no bound metadata could be found
     * @param <T> The type of metadata represented by the given key
     */
    <T> Optional<T> metadata(MetadataKey<T> key);

    /**
     * Offers a set of metadata to the source using the given {@link MetadataKey} for reference. This can be used
     * to maintain custom metadata across a source and allows for appropriately accessing data for one platform
     * where it would otherwise not exist within another.
     *
     * @param key The metadata key to bind the instance value to
     * @param instance The supplier responsible for providing the data, or null to remove metadata if it exists
     * @param <T> The type being bound as metadata
     */
    <T> void offer(MetadataKey<T> key, Supplier<T> instance);

    interface Factory {

        /**
         * Creates a new {@link PlatformSource} that represents the game console. This source can be used
         * to send messages directly to the console or additionally for other means.
         *
         * @return A platform source representing the game console
         */
        PlatformSource console();

        /**
         * Creates a source linked to an entity within the world, bound by a particular ID. If the given
         * UUID is bound to a player, this call will fail.
         *
         * @param uuid The UUID of the target entity
         * @return A platform source representing an entity existing within the world
         * @throws IllegalArgumentException If the given UUID is bound to a player
         */
        PlatformSource entity(UUID uuid);

    }

}
