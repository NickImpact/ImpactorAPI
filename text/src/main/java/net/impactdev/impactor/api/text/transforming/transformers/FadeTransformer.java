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

package net.impactdev.impactor.api.text.transforming.transformers;

import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.util.HSVLike;

public final class FadeTransformer extends ColorAlteringTransformer {

    public static FadeTransformer create(int steps, int gap, int start) {
        return new FadeTransformer(steps, gap, start);
    }

    private final int steps;
    private final int gap;
    private int start;

    private int phase;

    private FadeTransformer(int steps, int gap, int start) {
        this.steps = steps;
        this.gap = gap;
        this.phase = start;
    }

    @Override
    protected void advance() {
        this.phase = (this.phase + this.gap) % 360;
    }

    @Override
    protected TextColor color() {
        int hue = this.phase % 360;
        int spacer = 360 / this.steps;

        HSVLike hsv = HSVLike.hsvLike((hue + (this.phase * spacer)) % 360.0F / 360.0F, 1.0F, 1.0F);
        return TextColor.color(hsv);
    }

    @Override
    public void step() {
        this.start += this.gap;
        this.phase = this.start;
    }
}
