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

package net.impactdev.impactor.api.configuration;

import net.impactdev.impactor.api.Impactor;
import net.impactdev.impactor.api.configuration.key.ConfigKey;
import net.impactdev.impactor.api.utility.builders.Builder;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.function.Supplier;

public interface Config {

    /**
     * Gets the value of a given context key.
     *
     * @param key the key
     * @param <T> the key return type
     * @return the value mapped to the given key. May be null.
     */
    <T> T get(ConfigKey<T> key);

    /**
     * Reloads the configuration.
     */
    void reload();

    /**
     * Creates a new builder responsible for constructing a config
     *
     * @return A new configuration builder
     */
    static ConfigBuilder builder() {
        return Impactor.instance().builders().provide(ConfigBuilder.class);
    }

    interface ConfigBuilder extends Builder<Config> {

        /**
         * The class that contains a set of publicly static config keys.
         *
         * @param provider The class providing config keys
         * @return This builder, reflecting the given provider
         */
        ConfigBuilder provider(Class<?> provider);

        /**
         * Indicates the path that the config lives at.
         *
         * @param path The path to the config file
         * @return This builder, reflecting the given path
         */
        ConfigBuilder path(Path path);

        /**
         * If the configuration file cannot be located (should this be a first run), you can
         * provide a supplier to an input stream such that a default config will be generated
         * at time of initialization.
         *
         * <p>This call will additionally take care of closing the resource, so you don't
         * need to worry about resource management.</p>
         *
         * @param supplier A supplier which provides the input stream
         * @return This builder
         */
        ConfigBuilder provideIfMissing(Supplier<InputStream> supplier);

    }

}
