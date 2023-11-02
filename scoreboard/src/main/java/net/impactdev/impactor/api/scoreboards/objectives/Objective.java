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

package net.impactdev.impactor.api.scoreboards.objectives;

import net.impactdev.impactor.api.Impactor;
import net.impactdev.impactor.api.animations.resolvers.InitOnlyConfig;
import net.impactdev.impactor.api.components.animated.relative.RelativeComponentResolver;
import net.impactdev.impactor.api.scoreboards.ConfigurableScoreboardComponent;
import net.impactdev.impactor.api.scoreboards.updaters.listener.ListenerConfiguration;
import net.impactdev.impactor.api.scoreboards.updaters.scheduled.SchedulerConfiguration;

public interface Objective extends ConfigurableScoreboardComponent {

    static ObjectiveBuilder builder() {
        return Impactor.instance().builders().provide(ObjectiveBuilder.class);
    }

    static Objective constant(RelativeComponentResolver.Parser parser) {
        return builder().updater(InitOnlyConfig.create(parser)).build();
    }

    static Objective scheduled(SchedulerConfiguration.Provider config) {
        return builder().updater(config.configure(SchedulerConfiguration.builder())).build();
    }

    static Objective listening(ListenerConfiguration.Provider config) {
        return builder().updater(config.configure(ListenerConfiguration.builder())).build();
    }

}
