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
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;

import java.util.PrimitiveIterator;

public abstract class ColorFormatter extends AbstractFormatter implements DisplayFormatter.Stateful {

    private boolean locked = false;

    protected ColorFormatter(boolean locked) {
        this.locked = locked;
    }

    // TODO - This needs to consider children
    @Override
    public Component format(Context context) {
        this.calculateLength(context);

        Component input = context.require(DisplayFormatter.INPUT);
        if(input instanceof TextComponent) {
            if(input.style().color() != null) {
                return input;
            }

            String content = ((TextComponent) input).content();
            if(!content.isEmpty()) {
                final TextComponent.Builder builder = Component.text();

                final int[] holder = new int[1];
                for (final PrimitiveIterator.OfInt it = content.codePoints().iterator(); it.hasNext();) {
                    holder[0] = it.nextInt();
                    final Component comp = Component.text(new String(holder, 0, 1), input.style().color(this.color(context)));
                    this.advance(context);
                    builder.append(comp);
                }

                return builder.build();
            }
        }

        return input;
    }

    protected abstract void advance(Context context);

    protected abstract TextColor color(Context context);

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

}
