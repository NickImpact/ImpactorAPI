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

package net.impactdev.impactor.api.text.mini;

import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.Context;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.util.HSVLike;
import net.kyori.examination.ExaminableProperty;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.stream.Stream;

public final class FadeTag extends AbstractImpactorColorChangingTag {

    private static final String FADE = "fade";
    public static final TagResolver RESOLVER = TagResolver.resolver(FADE, FadeTag::create);

    private final int steps;
    private final int gap;
    private int phase;

    private static Tag create(final @NotNull ArgumentQueue queue, final Context context) {
        int steps = queue.popOr("Steps not specified")
                .asInt()
                .orElseThrow(() -> new IllegalArgumentException("Invalid steps configuration"));
        int gap = queue.popOr("Gap not specified")
                .asInt()
                .orElseThrow(() -> new IllegalArgumentException("Invalid gap configuration"));

        Argument phase = queue.peek();
        if(phase != null) {
            return new FadeTag(steps, gap, phase.asInt().orElseThrow(() -> new IllegalArgumentException("Invalid index configuration")));
        }

        return new FadeTag(steps, gap, 0);
    }

    private FadeTag(int steps, int gap, int phase) {
        this.steps = steps;
        this.gap = gap;
        this.phase = phase;
    }

    @Override
    protected void init() {}

    @Override
    protected void advanceColor() {
        this.phase = (this.phase + this.gap) % 360;
    }

    @Override
    protected TextColor color() {
        int hue = (this.phase + this.gap) % 360;
        int spacer = 360 / this.steps;

        HSVLike hsv = HSVLike.hsvLike((hue + (this.phase * spacer)) % 360.0F / 360.0F, 1.0F, 1.0F);
        return TextColor.color(hsv);
    }

    @Override
    public @NotNull Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(
                ExaminableProperty.of("phase", this.phase),
                ExaminableProperty.of("steps", this.steps),
                ExaminableProperty.of("gap", this.gap)
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FadeTag fadeTag = (FadeTag) o;
        return this.steps == fadeTag.steps && this.gap == fadeTag.gap && this.phase == fadeTag.phase;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.steps, this.gap, this.phase);
    }
}
