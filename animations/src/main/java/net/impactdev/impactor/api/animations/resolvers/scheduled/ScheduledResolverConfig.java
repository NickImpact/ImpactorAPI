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

package net.impactdev.impactor.api.animations.resolvers.scheduled;

import net.impactdev.impactor.api.components.animated.relative.RelativeComponentResolver;
import net.impactdev.impactor.api.scheduler.v2.Scheduler;
import net.impactdev.impactor.api.utility.builders.Builder;

public final class ScheduledResolverConfig implements RelativeComponentResolver.Configuration {

    private final Scheduler scheduler;
    private final RelativeComponentResolver.Parser parser;
    private final TaskProvider provider;

    public ScheduledResolverConfig(Configuration configuration) {
        this.parser = configuration.parser;
        this.scheduler = configuration.scheduler;
        this.provider = configuration.provider;
    }

    @Override
    public RelativeComponentResolver.Parser parser() {
        return this.parser;
    }

    public Scheduler scheduler() {
        return this.scheduler;
    }

    public TaskProvider provider() {
        return this.provider;
    }

    public static final class Configuration implements Builder<ScheduledResolverConfig> {

        private RelativeComponentResolver.Parser parser;
        private Scheduler scheduler;
        private TaskProvider provider;

        public Configuration parser(RelativeComponentResolver.Parser parser) {
            this.parser = parser;
            return this;
        }

        public Configuration scheduler(Scheduler scheduler) {
            this.scheduler = scheduler;
            return this;
        }

        public Configuration provider(TaskProvider provider) {
            this.provider = provider;
            return this;
        }

        @Override
        public ScheduledResolverConfig build() {
            return new ScheduledResolverConfig(this);
        }
    }
}
