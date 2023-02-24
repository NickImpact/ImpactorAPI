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
import net.impactdev.impactor.api.economy.accounts.Account;
import net.impactdev.impactor.api.platform.audience.LocalizedAudience;
import net.impactdev.impactor.api.utility.builders.Builder;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.function.BiFunction;

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
     * Specifies the symbol that should be used to represent the currency when formatting
     * in condensed mode.
     *
     * @return A symbol for this currency
     */
    Component symbol();

    /**
     *
     * @return
     */
    SymbolFormatting formatting();

    /**
     * The amount of money an account using this balance should start with, whether the
     * account has just been created or has processed a {@link Account#reset() reset} request.
     *
     * @return The standard starting balance for an account bound to this currency
     */
    BigDecimal defaultAccountBalance();

    int decimals();

    boolean primary();

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
    default Component format(final @NotNull BigDecimal amount) {
        return this.format(amount, true);
    }

    /**
     * Given a {@link BigDecimal}, formats the value into a component using the condensed pattern.
     * This condensed pattern simply uses the {@link #symbol()} versus the contextual names
     * of the currency to write the component.
     *
     * <p>In terms of formatting this amount, the given locale will be used for translating
     * the given number into the locale's method of displaying currencies.
     *
     * @param amount A {@link BigDecimal} representing some form of monetary value
     * @return A component representing the amount formatted into this currency's locale scheme
     */
    default Component format(final @NotNull BigDecimal amount, final @NotNull Locale locale) {
        return this.format(amount, true, locale);
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
    default Component format(final @NotNull BigDecimal amount, final boolean condensed) {
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
    Component format(final @NotNull BigDecimal amount, final boolean condensed, final @NotNull Locale locale);

    static CurrencyBuilder builder() {
        return Impactor.instance().builders().provide(CurrencyBuilder.class);
    }

    enum SymbolFormatting {
        BEFORE((currency, in) -> currency.symbol().append(in)),
        AFTER((currency, in) -> in.append(currency.symbol()));

        private final BiFunction<Currency, Component, Component> modifier;

        SymbolFormatting(BiFunction<Currency, Component, Component> modifier) {
            this.modifier = modifier;
        }

        public static SymbolFormatting fromIdentifier(final @NotNull String identifier) {
            for(SymbolFormatting type : values()) {
                if(identifier.equalsIgnoreCase(type.name())) {
                    return type;
                }
            }

            throw new IllegalArgumentException("Invalid formatting spec");
        }

        public Component modify(Currency currency, Component in) {
            return this.modifier.apply(currency, in);
        }
    }

    interface CurrencyBuilder extends Builder<Currency> {

        CurrencyBuilder key(final @NotNull Key key);

        CurrencyBuilder name(final @NotNull Component name);

        CurrencyBuilder plural(final @NotNull Component plural);

        CurrencyBuilder symbol(final @NotNull Component symbol);

        CurrencyBuilder formatting(final @NotNull SymbolFormatting format);

        CurrencyBuilder starting(final @NotNull BigDecimal amount);

        CurrencyBuilder decimals(final int decimals);

        CurrencyBuilder primary();
    }

}
