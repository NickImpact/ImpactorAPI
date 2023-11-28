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

package net.impactdev.impactor.api.scoreboards.display.resolvers.scheduled;

import net.impactdev.impactor.api.Impactor;
import net.impactdev.impactor.api.scheduler.Ticks;
import net.impactdev.impactor.api.scheduler.v2.Scheduler;
import net.impactdev.impactor.api.scoreboards.display.resolvers.config.ConfigurationSupplier;
import net.impactdev.impactor.api.scoreboards.display.resolvers.config.ResolverBuilder;
import net.impactdev.impactor.api.scoreboards.display.resolvers.config.ResolverConfiguration;

import java.util.concurrent.TimeUnit;

public interface SchedulerConfiguration extends ResolverConfiguration {

    Scheduler scheduler();

    static Configuration builder() {
        return Impactor.instance().builders().provide(Configuration.class);
    }

    interface Configuration extends ResolverBuilder<SchedulerConfiguration, Configuration> {

        TaskProperties scheduler(Scheduler scheduler);

    }

    @FunctionalInterface
    interface ConfigSupplier extends ConfigurationSupplier<SchedulerConfiguration, Configuration> {}

    interface TaskProperties {

        default Configuration repeating(long interval, TimeUnit unit) {
            return this.repeating(0, interval, unit);
        }

        Configuration repeating(long delay, long interval, TimeUnit unit);

        default Configuration repeating(Ticks interval) {
            return this.repeating(Ticks.zero(), interval);
        }

        Configuration repeating(Ticks delay, Ticks interval);

        Configuration delayed(long delay, TimeUnit unit);

        Configuration delayed(Ticks delay);

    }

}
