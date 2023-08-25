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

package net.impactdev.impactor.api.text.components;
 
import com.google.common.base.Strings;
import net.impactdev.impactor.api.utility.builders.Builder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentBuilder;
import net.kyori.adventure.text.ComponentBuilderApplicable;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import org.jetbrains.annotations.NotNull;

public final class ProgressBarComponent implements ComponentLike, ComponentBuilderApplicable {

    private final float value;
    private final float max;

    private final int size;
    private final char character;

    private final TextColor filled;
    private final TextColor background;
    private final Style style;

    private ProgressBarComponent(ProgressComponentBuilder builder) {
        this.value = builder.value;
        this.max = builder.max;
        this.size = builder.size;
        this.character = builder.character;
        this.filled = builder.filled;
        this.background = builder.background;
        this.style = builder.style;
    }

    @Override
    public @NotNull Component asComponent() {
        double percentage = this.value / this.max;
        int size = (int) (this.size * percentage);

        Component result = Component.text().style(this.style).build();
        String character = String.valueOf(this.character);
        result = result.append(Component.text(Strings.repeat(character, size)).color(this.filled));
        return result.append(Component.text(Strings.repeat(character, this.size - size)).color(this.background));
    }

    @Override
    public void componentBuilderApply(@NotNull ComponentBuilder<?, ?> builder) {
        builder.append(this.asComponent());
    }

    public static ProgressComponentBuilder builder() {
        return new ProgressComponentBuilder();
    }

    public static final class ProgressComponentBuilder implements Builder<ProgressBarComponent> {

        private float value;
        private float max;
        private int size;
        private char character;
        private TextColor filled;
        private TextColor background;
        private Style style = Style.empty();

        public ProgressComponentBuilder value(float value) {
            this.value = value;
            return this;
        }

        public ProgressComponentBuilder max(float max) {
            this.max = max;
            return this;
        }

        public ProgressComponentBuilder size(int size) {
            this.size = size;
            return this;
        }

        public ProgressComponentBuilder character(char character) {
            this.character = character;
            return this;
        }

        public ProgressComponentBuilder filled(TextColor color) {
            this.filled = color;
            return this;
        }

        public ProgressComponentBuilder background(TextColor color) {
            this.background = color;
            return this;
        }

        public ProgressComponentBuilder style(Style style) {
            this.style = style;
            return this;
        }

        @Override
        public ProgressBarComponent build() {
            return new ProgressBarComponent(this);
        }
    }
}
