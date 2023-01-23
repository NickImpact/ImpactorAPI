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

package net.impactdev.impactor.api.plugin;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.impactdev.impactor.api.platform.plugins.PluginMetadata;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 *
 */
public final class PluginRegistry {

    private static final Map<PluginMetadata, ImpactorPlugin> plugins = new HashMap<>();
    private static ImmutableMap<PluginMetadata, ImpactorPlugin> queue;

    private static boolean locked = false;

    public static void lock() {
        locked = true;
        queue = registeredInLoadOrder();
    }

    public static void register(PluginMetadata metadata, ImpactorPlugin plugin) {
        if(locked) {
            throw new IllegalStateException("Plugin Registry has locked, no more plugins can be registered");
        }
        plugins.put(metadata, plugin);
    }

    public static Map<PluginMetadata, ImpactorPlugin> registered() {
        return plugins;
    }

    public static Map<PluginMetadata, ImpactorPlugin> allInLoadOrder() {
        return Optional.ofNullable(queue).orElse(ImmutableMap.of());
    }

    public static Optional<ImpactorPlugin> fetch(PluginMetadata metadata) {
        return Optional.ofNullable(plugins.get(metadata));
    }

    public static Optional<ImpactorPlugin> fetchByID(String id) {
        return registered().entrySet().stream()
                .filter(entry -> entry.getKey().id().equals(id))
                .map(Map.Entry::getValue)
                .findFirst();
    }

    private static ImmutableMap<PluginMetadata, ImpactorPlugin> registeredInLoadOrder() {
        Map<PluginMetadata, ImpactorPlugin> result = Maps.newLinkedHashMap();
        Map<String, DependencyAllocation> allocations = new HashMap<>();

        plugins.forEach((metadata, plugin) -> {
            allocations.computeIfAbsent(metadata.id(), ignore -> new DependencyAllocation(metadata, plugin));
            metadata.dependencies().forEach(dependency -> {
                Optional<PluginMetadata> lookup = registered().keySet().stream()
                                .filter(m -> m.id().equals(dependency.id()))
                                .findFirst();
                if(!lookup.isPresent() && !dependency.optional()) {
                    throw new IllegalStateException("Required dependency missing!");
                }

                lookup.ifPresent(l -> {
                    DependencyAllocation allocation = allocations.computeIfAbsent(
                            dependency.id(),
                            ignore -> new DependencyAllocation(l, registered().get(l))
                    );
                    allocation.requires += 1;
                });
            });
        });

        allocations.values()
                .stream()
                .sorted()
                .forEach(allocation -> result.put(allocation.metadata, allocation.plugin));

        return ImmutableMap.copyOf(result);
    }

    private static final class DependencyAllocation implements Comparable<DependencyAllocation> {

        private final PluginMetadata metadata;
        private final ImpactorPlugin plugin;

        private int requires = 0;

        public DependencyAllocation(PluginMetadata metadata, ImpactorPlugin plugin) {
            this.metadata = metadata;
            this.plugin = plugin;
        }

        @Override
        public int compareTo(@NotNull PluginRegistry.DependencyAllocation o) {
            return Integer.compare(this.requires, o.requires);
        }
    }

}
