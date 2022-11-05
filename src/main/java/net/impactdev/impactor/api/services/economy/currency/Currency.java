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

package net.impactdev.impactor.api.services.economy.currency;

import net.impactdev.impactor.api.Impactor;
import net.impactdev.impactor.api.builders.Buildable;
import net.impactdev.impactor.api.builders.Builder;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;

import java.math.BigDecimal;

public interface Currency {

    Key key();

    boolean primary();

    Component name();

    Component plural();

    Component symbol();

    Component format(final BigDecimal amount);

    BigDecimal starting();

    int decimals();

    static CurrencyBuilder builder() {
        return Impactor.instance().builders().provide(CurrencyBuilder.class);
    }

    interface CurrencyBuilder extends Builder<Currency> {

        CurrencyBuilder key(final Key key);

        CurrencyBuilder primary(final boolean primary);

        CurrencyBuilder name(final Component name);

        CurrencyBuilder plural(final Component plural);

        CurrencyBuilder symbol(final Component symbol);

        CurrencyBuilder starting(final BigDecimal amount);

        CurrencyBuilder decimals(final int decimals);

    }

}
