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

package net.impactdev.impactor.api.scoreboards.updaters.scheduled;

import net.impactdev.impactor.api.scheduler.Ticks;
import net.impactdev.impactor.api.scheduler.v2.Scheduler;
import net.impactdev.impactor.api.scheduler.v2.Schedulers;
import net.impactdev.impactor.api.scoreboards.updaters.UpdaterConfiguration;
import net.kyori.adventure.key.Key;

import java.util.concurrent.TimeUnit;

public interface ScheduledConfiguration extends UpdaterConfiguration<ScheduledUpdater> {

    Scheduler scheduler();

    interface ProvideScheduler {

        default ConfigureTask scheduler(Key key) {
            return this.scheduler(Schedulers.require(key));
        }

        ConfigureTask scheduler(Scheduler scheduler);

    }

    interface ConfigureTask {

        default ScheduledConfiguration repeating(long interval, TimeUnit unit) {
            return this.repeating(0, interval, unit);
        }

        ScheduledConfiguration repeating(long delay, long interval, TimeUnit unit);

        default ScheduledConfiguration repeating(Ticks interval) {
            return this.repeating(Ticks.zero(), interval);
        }

        ScheduledConfiguration repeating(Ticks delay, Ticks interval);

        ScheduledConfiguration delayed(long delay, TimeUnit unit);

        ScheduledConfiguration delayed(Ticks delay);

    }

}
