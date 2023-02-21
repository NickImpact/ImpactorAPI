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

package net.impactdev.impactor.api.economy.currency;

import net.impactdev.impactor.api.Impactor;
import net.impactdev.impactor.api.platform.audience.LocalizedAudience;
import net.impactdev.impactor.api.utility.builders.Builder;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;

import java.math.BigDecimal;
import java.util.Locale;

/**
 *
 */
public interface Currency {

    /**
     * A key used to represent the currency. This key should be unique to any other
     * registered currency, and will be used for details such as data management.
     *
     * @return A key representing the currency
     */
    Key key();

    /**
     * Represents the name of the currency when used in a singular context. For example, when
     * detailing $1, we would use this to format to 1 Dollar.
     *
     * @return A component representing the singular context name for this currency
     */
    Component singular();

    /**
     * Represents the name of the currency when used in a context outside singular requirements.
     * For example, when detailing $0 or $100, we would have 0 Dollars or 100 Dollars.
     *
     * @return A component representing the plural context name for this currency
     */
    Component plural();

    /**
     * Specifies details regarding the symbol used to format this currency and how it should
     * be formatted. This symbol metadata is composed of a {@link Component} representing
     * the actual symbol, and a {@link Symbol.Direction direction} indicating where in the
     * formatted component the symbol should lie.
     *
     * @return A symbol for this currency
     */
    Currency.Symbol symbol();

    /**
     * Given a {@link BigDecimal}, formats the value into a component using the condensed pattern.
     * This condensed pattern simply uses the {@link #symbol()} versus the contextual names
     * of the currency to write the component.
     *
     * <p>In terms of formatting this amount, the value of {@link Locale#ROOT} will be used
     * to determine how exactly to write the number.
     *
     * @param amount A {@link BigDecimal} representing some form of monetary value
     * @return A component representing the amount formatted into this currency's locale scheme
     */
    default Component format(final BigDecimal amount) {
        return this.format(amount, true);
    }

    /**
     * Given a {@link BigDecimal}, formats the value into a component, optionally condensed
     * or formatted using the {@link #singular()} or {@link #plural()} names, given the value
     * specified.
     *
     * <p>In terms of formatting this amount, the value of {@link Locale#ROOT} will be used
     * to determine how exactly to write the number.
     *
     * @param amount A {@link BigDecimal} representing some form of monetary value
     * @param condensed Whether the formatter should use the symbol for the currency, or delegate
     *                  to the contextual names
     * @return A component representing the amount formatted into this currency's locale scheme
     */
    default Component format(final BigDecimal amount, boolean condensed) {
        return this.format(amount, condensed, Locale.ROOT);
    }

    /**
     * Given a {@link BigDecimal}, formats the value into a component, optionally condensed
     * or formatted using the {@link #singular()} or {@link #plural()} names, given the value
     * specified.
     *
     * <p>When formatting the amount specified, this will use the given locale to ensure
     * the formatted value meets the locale's rules. For instance, this will replace dots
     * with commas where necessary. If you have an instance of a {@link net.impactdev.impactor.api.platform.sources.PlatformSource},
     * you can find the locale through {@link LocalizedAudience#locale()}.
     *
     * @param amount A {@link BigDecimal} representing some form of monetary value
     * @param condensed Whether the formatter should use the symbol for the currency, or delegate
     *                  to the contextual names
     * @param locale The locale that should be used to format the given amount
     * @return A component representing the amount formatted into this currency's locale scheme
     */
    Component format(final BigDecimal amount, boolean condensed, Locale locale);

    /**
     *
     * @return
     */
    BigDecimal starting();

    /**
     *
     * @return
     */
    int decimals();

    interface Symbol {

        Component symbol();

        Direction direction();

        enum Direction {
            LEFT,
            RIGHT
        }
    }

    static CurrencyBuilder builder() {
        return Impactor.instance().builders().provide(CurrencyBuilder.class);
    }

    interface CurrencyBuilder extends Builder<Currency> {

        CurrencyBuilder key(final Key key);

        CurrencyBuilder name(final Component name);

        CurrencyBuilder plural(final Component plural);

        CurrencyBuilder symbol(final Component symbol, Symbol.Direction direction);

        CurrencyBuilder starting(final BigDecimal amount);

        CurrencyBuilder decimals(final int decimals);

    }

}
