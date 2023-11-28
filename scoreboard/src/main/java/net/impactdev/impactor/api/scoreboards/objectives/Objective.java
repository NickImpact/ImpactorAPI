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
import net.impactdev.impactor.api.annotations.Minecraft;
import net.impactdev.impactor.api.scoreboards.display.Displayable;
import net.impactdev.impactor.api.scoreboards.display.resolvers.config.ResolverConfiguration;
import net.impactdev.impactor.api.scoreboards.display.resolvers.listening.ListenerConfiguration;
import net.impactdev.impactor.api.scoreboards.display.resolvers.scheduled.SchedulerConfiguration;
import net.impactdev.impactor.api.scoreboards.score.ScoreFormatter;
import net.impactdev.impactor.api.utility.builders.Builder;
import org.jetbrains.annotations.Nullable;

public interface Objective extends Displayable {

    /**
     * Represents a parent formatter that will be used to decorate scores for any score, within the objective,
     * that does not have its own formatter set.
     *
     * <p>This particular feature requires Minecraft 1.20.3. It serves no functionality otherwise.</p>
     *
     * @return A formatter for scores, or null
     * @since 5.2.0
     */
    @Nullable
    @Minecraft("1.20.3")
    ScoreFormatter formatter();

    static ObjectiveBuilder builder() {
        return Impactor.instance().builders().provide(ObjectiveBuilder.class);
    }

    static ObjectiveBuilder constant() {
        return builder();
    }

    static ObjectiveBuilder scheduled(SchedulerConfiguration.ConfigSupplier config) {
        return builder().resolver(config.create(SchedulerConfiguration.builder()));
    }

    static ObjectiveBuilder listening(ListenerConfiguration.ConfigSupplier config) {
        return builder().resolver(config.create(ListenerConfiguration.builder()));
    }

    interface ObjectiveBuilder extends Builder<Objective> {

        ObjectiveBuilder resolver(ResolverConfiguration configuration);

        @Minecraft("1.20.3")
        ObjectiveBuilder formatter(ScoreFormatter formatter);

    }

}
