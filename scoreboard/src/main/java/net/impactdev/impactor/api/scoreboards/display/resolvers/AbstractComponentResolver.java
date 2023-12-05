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

package net.impactdev.impactor.api.scoreboards.display.resolvers;

import net.impactdev.impactor.api.platform.sources.PlatformSource;
import net.impactdev.impactor.api.scoreboards.display.formatters.DisplayFormatter;
import net.impactdev.impactor.api.utility.Context;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractComponentResolver implements ComponentResolver {

    private final ComponentProvider provider;
    private final @Nullable DisplayFormatter formatter;

    protected AbstractComponentResolver(ComponentProvider provider, @Nullable DisplayFormatter formatter) {
        this.provider = provider;
        this.formatter = formatter;
    }

    @Override
    public Component resolve(PlatformSource viewer, Context context) {
        Component result = this.provider.parse(viewer, context);

        if(this.formatter != null) {
            result = this.formatter.format(result);

            if(this.formatter instanceof DisplayFormatter.Stateful stateful) {
                stateful.step();
            }
        }
        
        return result;
    }

    @Override
    public ComponentProvider provider() {
        return this.provider;
    }
}
