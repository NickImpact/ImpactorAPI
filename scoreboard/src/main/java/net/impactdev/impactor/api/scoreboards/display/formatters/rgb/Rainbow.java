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

package net.impactdev.impactor.api.scoreboards.display.formatters.rgb;

import net.impactdev.impactor.api.scoreboards.display.formatters.DisplayFormatter;
import net.impactdev.impactor.api.scoreboards.display.formatters.ColorFormatter;
import net.impactdev.impactor.api.utility.builders.Builder;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.util.HSVLike;

public final class Rainbow extends ColorFormatter implements DisplayFormatter.Stateful {

    private final boolean reversed;
    private final int increment;

    private int phase;
    private int index;

    private Rainbow(RainbowFormatterConfiguration config) {
        super(config.locked);
        this.phase = config.phase;
        this.reversed = config.reversed;
        this.increment = config.increment;
    }

    @Override
    protected void advance(int length) {
        if (this.reversed) {
            if (this.index == 0) {
                this.index = length - 1;
            } else {
                this.index--;
            }
        } else {
            this.index++;
        }
    }

    @Override
    protected TextColor color(int length) {
        final float index = this.index;
        final float hue = (index / length + this.phase / 10f) % 1;
        return TextColor.color(HSVLike.hsvLike(hue, 1f, 1f));
    }

    @Override
    public void step() {
        this.phase += this.increment;
    }

    @Override
    public int increment() {
        return this.increment;
    }

    public static RainbowFormatterConfiguration builder() {
        return new RainbowFormatterConfiguration();
    }

    public static final class RainbowFormatterConfiguration implements Builder<Rainbow> {

        private int frames;
        private int phase;
        private boolean reversed;

        private boolean locked;
        private int increment;

        public RainbowFormatterConfiguration frames(int frames) {
            this.frames = frames;
            return this;
        }

        public RainbowFormatterConfiguration phase(int phase) {
            this.phase = phase;
            return this;
        }

        public RainbowFormatterConfiguration reversed(boolean state) {
            this.reversed = state;
            return this;
        }

        public RainbowFormatterConfiguration locked(boolean locked) {
            this.locked = locked;
            return this;
        }

        public RainbowFormatterConfiguration increment(int increment) {
            this.increment = increment;
            return this;
        }

        @Override
        public Rainbow build() {
            return new Rainbow(this);
        }

    }
}
