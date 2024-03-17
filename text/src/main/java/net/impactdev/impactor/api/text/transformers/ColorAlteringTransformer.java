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

package net.impactdev.impactor.api.text.transformers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.jetbrains.annotations.NotNull;

import java.util.PrimitiveIterator;

public abstract class ColorAlteringTransformer implements TextTransformer {

    @Override
    public final Component transform(@NotNull Component input) {
        if(input instanceof TextComponent) {
            TextComponent parent = (TextComponent) input;
            if(input.style().color() != null) {
                return input;
            }

            String content = parent.content();
            if(content.length() > 0) {
                final TextComponent.Builder builder = Component.text();

                final int[] holder = new int[1];
                for (final PrimitiveIterator.OfInt it = content.codePoints().iterator(); it.hasNext();) {
                    holder[0] = it.nextInt();
                    final Component comp = Component.text(new String(holder, 0, 1), input.style().color(this.color()));
                    this.advance();
                    builder.append(comp);
                }

                return builder.build();
            }
        }

        return input;
    }

    protected abstract void advance();

    protected abstract TextColor color();

}
