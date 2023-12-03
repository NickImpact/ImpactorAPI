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

package net.impactdev.impactor.api.scoreboards.display.formatters;

import net.impactdev.impactor.api.utility.Context;
import net.kyori.adventure.pointer.Pointer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.flattener.ComponentFlattener;
import net.kyori.adventure.text.format.TextColor;

import java.util.Collections;
import java.util.PrimitiveIterator;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class ColorFormatter extends AbstractFormatter implements DisplayFormatter.Stateful {

    private static final ComponentFlattener LENGTH_CALCULATOR = ComponentFlattener.builder()
            .mapper(TextComponent.class, TextComponent::content)
            .unknownMapper(x -> "_")
            .build();

    private boolean locked = false;

    protected ColorFormatter(boolean locked) {
        this.locked = locked;
    }

    @Override
    public Component format(Component root) {
        AtomicInteger length = new AtomicInteger(0);
        LENGTH_CALCULATOR.flatten(root, s -> length.set(s.codePointCount(0, s.length())));

        return Component.empty();
    }

    protected abstract void advance(int length);
    protected abstract TextColor color(int length);

    @Override
    public boolean locked() {
        return this.locked;
    }

    @Override
    public void lock() {
        this.locked = true;
    }

    @Override
    public void unlock() {
        this.locked = false;
    }

    private Component apply(Component component, int totalSize) {
        if(component instanceof TextComponent text) {
            // If this component has a color, we will not overwrite it. Instead, we will
            // simply track the length for the remainder of the text to format.
            if(text.style().color() != null) {
                final String content = text.content();
                final int length = content.codePointCount(0, content.length());

                for(int i = 0; i < length; i++) {
                    this.advance(totalSize);
                }

                return text;
            }

            String content = text.content();
            if(content.length() > 0) {
                final TextComponent.Builder parent = Component.text();

                final int[] holder = new int[1];
                for(final PrimitiveIterator.OfInt it = content.codePoints().iterator(); it.hasNext();) {
                    holder[0] = it.nextInt();
                    final Component letter = Component.text(new String(holder, 0, 1), component.style().color(this.color(totalSize)));
                    this.advance(totalSize);

                    parent.append(letter);
                }

                return parent.build();
            }
        } else {
            final Component other = component.children(Collections.emptyList()).colorIfAbsent(this.color(totalSize));
            this.advance(totalSize);

            return other;
        }

        return Component.empty().mergeStyle(component);
    }

}
