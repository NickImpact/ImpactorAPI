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

package net.impactdev.impactor.api.economy.accounts;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import net.impactdev.impactor.api.economy.currency.Currency;
import net.impactdev.impactor.api.economy.transactions.EconomyTransaction;
import net.impactdev.impactor.api.utility.builders.Builder;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * <h2>Account Definition</h2>
 * Represents an economic based account which is bound to a particular currency and owner.
 * Upon creation, an account will feature a starting balance, either defined by a currency's
 * default balance, or by being created from an {@link AccountBuilder} via {@link AccountBuilder#balance(BigDecimal)}.
 *
 * <br><br>
 * <h2>Account Ownership</h2>
 * An account is capable of being owned by any source which is identifiable from a {@link UUID}.
 * As such, this means an account need not be owned by a player, but perhaps the server or
 * a plugin implementation. Additionally, this opens up the option for banks that can be accessed
 * by a group or even a singular person.
 */
public interface Account {

    /**
     * The currency this account represents.
     *
     * @return
     */
    @NotNull
    Currency currency();

    /**
     * The owner of the account, which need not be a player.
     * @return
     */
    @NotNull
    UUID owner();

    @NotNull
    BigDecimal balance();

    @NotNull
    EconomyTransaction set(BigDecimal amount);

    @NotNull
    EconomyTransaction withdraw(BigDecimal amount);

    @NotNull
    EconomyTransaction deposit(BigDecimal amount);

    @NotNull
    EconomyTransaction transfer(Account to, BigDecimal amount);

    @NotNull
    EconomyTransaction reset();

    interface AccountBuilder extends Builder<Account> {

        @NotNull
        @Contract("_ -> this")
        @CanIgnoreReturnValue
        AccountBuilder currency(final @NotNull Currency currency);

        @NotNull
        @Contract("_ -> this")
        @CanIgnoreReturnValue
        AccountBuilder owner(final @NotNull UUID uuid);

        @NotNull
        @Contract("_ -> this")
        @CanIgnoreReturnValue
        AccountBuilder balance(final @NotNull BigDecimal balance);

    }

}
