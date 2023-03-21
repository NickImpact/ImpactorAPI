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
import net.impactdev.impactor.api.economy.transactions.details.EconomyResultType;
import net.impactdev.impactor.api.economy.transactions.EconomyTransaction;
import net.impactdev.impactor.api.economy.transactions.EconomyTransferTransaction;
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
     * The currency this account represents. Each account is linked to a particular currency,
     * versus an account holding onto a set of currencies.
     *
     * @return The currency this account reflects
     */
    @NotNull
    Currency currency();

    /**
     * The owner of the account, which need not be a player. For instance, this could be a UUID
     * that represents the server, or a newly generated UUID that could be used to denote a bank.
     *
     * @return The UUID of the owner of the account
     */
    @NotNull
    UUID owner();

    /**
     * When <code>true</code>, indicates that this account is not tied to a particular user.
     *
     * <p>Examples of virtual accounts include:
     *  <ul>
     *      <li>A global server account</li>
     *      <li>A 'bank account', shared among multiple users</li>
     *      <li>An account for a non-player entity</li>
     *  </ul>
     * </p>
     *
     * @return <code>true</code> if this account represents a virtual entity, <code>false</code>
     * otherwise
     */
    boolean virtual();

    /**
     * Indicates the current outstanding balance of an account. When updating an account's
     * balance, you'll want to make use of the below methods to achieve this task.
     *
     * @return The current balance of this account.
     */
    @NotNull
    BigDecimal balance();

    /**
     * Sets this account's balance to the given amount specified. This value should conform
     * to the specified limits denoted by the implementation. Any value outside the implementation's
     * allowed constraints, if any, will result in a failed transaction, denoted by
     * {@link EconomyTransaction#result()} with a return value of {@link EconomyResultType#FAILED}
     * or {@link EconomyResultType#CANCELLED}.
     *
     * @param amount The amount to set the account's balance to
     * @return A transaction report indicating the result of the operation
     */
    @NotNull
    @CanIgnoreReturnValue
    EconomyTransaction set(BigDecimal amount);

    /**
     * Withdraws from the account's balance by the specified amount. This value should
     * conform to the specified limits denoted by the implementation. For instance, an account
     * may not be allowed to go beyond 0 and into the negatives, in which case, the implementation
     * should report a result of {@link EconomyResultType#NOT_ENOUGH_FUNDS}. Should any other
     * failure occur, {@link EconomyResultType#FAILED} or {@link EconomyResultType#CANCELLED}.
     *
     * @param amount The amount to withdraw from the account
     * @return A transaction report indicating the result of the operation
     */
    @NotNull
    @CanIgnoreReturnValue
    EconomyTransaction withdraw(BigDecimal amount);

    /**
     * Deposits the amount specified into the account. This value should conform to the
     * specified limits denoted by the implementation. For instance, an implementation might
     * impose max balance limits on an account. In the event such a restriction is imposed,
     * any use of this operation that would cause the account to push beyond that limit
     * will fail and be marked with a result of {@link EconomyResultType#NO_REMAINING_SPACE}.
     * Any other sort of failures will result in {@link EconomyResultType#FAILED} or
     * {@link EconomyResultType#CANCELLED}.
     *
     * @param amount The amount to deposit into the account
     * @return A transaction report indicating the result of the operation
     */
    @NotNull
    @CanIgnoreReturnValue
    EconomyTransaction deposit(BigDecimal amount);

    /**
     * Performs a bank transfer of the specified amount from this account to the target
     * account supplied as <code>to</code>. This effectively mirrors a withdrawal and deposit
     * interaction, validating both requirements before proceeding with the transfer.
     *
     * @param to The account money is being transferred to
     * @param amount The amount to transfer
     * @return A transaction report indicating the result of the operation
     */
    @NotNull
    @CanIgnoreReturnValue
    EconomyTransferTransaction transfer(Account to, BigDecimal amount);

    /**
     * Resets an account's balance to the defined starting balance of the currency,
     * or a config's given override, if set.
     *
     * @return A transaction report indicating the result of the operation
     */
    @NotNull
    @CanIgnoreReturnValue
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

        /**
         * Marks an account as virtual, if it can be marked as such. If an existing account that has the same
         * UUID as this account is available, this functionality will be overwritten to match that of the already
         * available account's.
         *
         * @return This builder
         */
        @NotNull
        @Contract("-> this")
        @CanIgnoreReturnValue
        AccountBuilder virtual();

    }

    @FunctionalInterface
    interface AccountModifier {

        AccountBuilder modify(AccountBuilder builder);

    }

}
