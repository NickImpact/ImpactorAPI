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

package net.impactdev.impactor.api.economy.events;

import net.impactdev.impactor.api.economy.EconomyService;
import net.impactdev.impactor.api.events.ImpactorEvent;
import net.impactdev.impactor.api.platform.PluginMetadata;
import org.jetbrains.annotations.Range;

import java.util.function.Supplier;

/**
 * Provides a means for suggesting an economy service for the plugin to use as it initializes. By default,
 * Impactor attempts to provide its own service, at a priority value of 0. Any listeners wishing to override
 * this service should supply their service with a priority higher than 0. When all listeners have been processed,
 * the highest priority service will act as the controlling service. In the event of a tie in priority, the first
 * service to attempt registration will be selected.
 */
public interface SuggestEconomyServiceEvent extends ImpactorEvent {

    /**
     * Suggests the given economy service that should act as the APIs implementation end-point.
     * <strong>This should only ever be called once per listener!</strong>
     *
     * @param suggestor A set of metadata detailing who is supplying the service
     * @param service A supplier used to create the service
     * @param priority The priority of the service
     */
    void suggest(
            final PluginMetadata suggestor,
            final Supplier<EconomyService> service,
            final @Range(from = 0, to = Integer.MAX_VALUE) int priority
    );

}
