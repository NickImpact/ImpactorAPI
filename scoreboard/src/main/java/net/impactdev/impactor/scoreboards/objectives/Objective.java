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

package net.impactdev.impactor.scoreboards.objectives;

import net.impactdev.impactor.api.Impactor;
import net.impactdev.impactor.scoreboards.ScoreboardDisplayable;
import net.impactdev.impactor.scoreboards.updaters.constant.ConstantResolver;
import net.impactdev.impactor.scoreboards.updaters.listener.ListenerConfiguration;
import net.impactdev.impactor.scoreboards.updaters.listener.ListenerResolver;
import net.impactdev.impactor.scoreboards.updaters.scheduled.ScheduledResolver;
import net.impactdev.impactor.scoreboards.updaters.scheduled.SchedulerConfiguration;
import net.kyori.adventure.text.Component;

import java.util.function.Supplier;

public interface Objective extends ScoreboardDisplayable {

    static ObjectiveBuilder builder() {
        return Impactor.instance().builders().provide(ObjectiveBuilder.class);
    }

    static Objective constant(Supplier<Component> supplier) {
        return builder().resolver(supplier).updater(ConstantResolver.create()).build();
    }

    static Objective scheduled(Supplier<Component> supplier, SchedulerConfiguration config) {
        return builder().resolver(supplier).updater(ScheduledResolver.create(config)).build();
    }

    static Objective listening(Supplier<Component> supplier, ListenerConfiguration config) {
        return builder().resolver(supplier).updater(ListenerResolver.create(config)).build();
    }

}
