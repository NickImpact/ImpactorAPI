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

package net.impactdev.impactor.api.scoreboards.updaters.listener;

import net.impactdev.impactor.api.Impactor;
import net.impactdev.impactor.api.scoreboards.relative.RelativeAnimatable;
import net.impactdev.impactor.api.utility.builders.Builder;
import net.impactdev.impactor.api.scoreboards.ConfigurableScoreboardComponent;
import net.impactdev.impactor.api.scoreboards.updaters.ResolverConfiguration;
import net.kyori.event.EventSubscription;

public interface ListenerConfiguration extends ResolverConfiguration.Transformable {

    Subscriber subscriber();

    @FunctionalInterface
    interface Subscriber {

        EventSubscription subscribe(RelativeAnimatable displayable);

    }

    @FunctionalInterface
    interface Provider {

        ListenerConfiguration configure(ListenerConfiguration.ConfigBuilder builder);

    }

    static ConfigBuilder builder() {
        return Impactor.instance().builders().provide(ConfigBuilder.class);
    }

    interface ConfigBuilder extends Builder<ListenerConfiguration>, Transforming<ConfigBuilder> {

        ConfigBuilder subscriber(Subscriber subscriber);

    }

}
