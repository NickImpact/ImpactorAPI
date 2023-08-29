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

package net.impactdev.impactor.scoreboards.updaters.scheduled;

import net.impactdev.impactor.api.Impactor;
import net.impactdev.impactor.api.utility.builders.Builder;
import net.impactdev.impactor.api.text.transforming.transformers.TextTransformer;
import net.impactdev.impactor.scoreboards.updaters.ComponentResolver;
import net.kyori.adventure.text.Component;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * Represents a component updater which refreshes the resolved component on after a scheduled amount of time.
 * This type of updater is capable of running async where possible.
 */
public interface ScheduledResolver extends ComponentResolver {

    Duration interval();

    boolean async();

    static ScheduledResolver create(SchedulerConfiguration config) {
        return config.configure(Impactor.instance().builders().provide(ScheduledResolverBuilder.class));
    }

    interface ScheduledResolverBuilder extends Builder<ScheduledResolver> {

        ScheduledResolverBuilder text(Supplier<Component> provider);

        ScheduledResolverBuilder interval(long time, TimeUnit unit);

        ScheduledResolverBuilder async();

        ScheduledResolverBuilder transformer(TextTransformer transformer);

    }

}
