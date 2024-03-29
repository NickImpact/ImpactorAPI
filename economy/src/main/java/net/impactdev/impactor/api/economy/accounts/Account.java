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
import kotlin.ReplaceWith;
import net.impactdev.impactor.api.Impactor;
import net.impactdev.impactor.api.economy.currency.Currency;
import net.impactdev.impactor.api.economy.transactions.details.EconomyResultType;
import net.impactdev.impactor.api.economy.transactions.EconomyTransaction;
import net.impactdev.impactor.api.economy.transactions.EconomyTransferTransaction;
import net.impactdev.impactor.api.utility.builders.Builder;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

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
     * <ul>
     *     <li>A global server account</li>
     *     <li>A 'bank account', shared among multiple users</li>
     *     <li>An account for a non-player entity</li>
     * </ul>
     *
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
     * Asynchronously fetches the current balance on an account. This method properly considers the possibility
     * that an account's finer details will need to be computed from a non-cacheable data store.
     *
     * @return A completable future which computes the balance of this account
     * @since 5.1.0
     * @deprecated Early on, there were worries transactions would also need to be made async
     * to better support external stores. To better serve the API and thread usage, this design
     * is being deprecated in favor of retaining the original account access future.
     */
    @NotNull
    @Deprecated(forRemoval = true)
    @ApiStatus.ScheduledForRemoval(inVersion = "6.0.0")
    default CompletableFuture<BigDecimal> balanceAsync() {
        return CompletableFuture.completedFuture(this.balance());
    }

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
     * Asynchronously sets this account's balance to the given amount specified. This value should conform
     * to the specified limits denoted by the implementation. Any value outside the implementation's
     * allowed constraints, if any, will result in a failed transaction, denoted by
     * {@link EconomyTransaction#result()} with a return value of {@link EconomyResultType#FAILED}
     * or {@link EconomyResultType#CANCELLED}.
     *
     * <p>Unlike {@link #set(BigDecimal)}, this method properly adheres to implementations which might
     * require stateful transactions to ensure data safety.</p>
     *
     * @return A completable future which reports an {@link EconomyTransaction} describing the result
     * of the operation
     * @since 5.1.0
     * @deprecated Early on, there were worries transactions would also need to be made async
     * to better support external stores. To better serve the API and thread usage, this design
     * is being deprecated in favor of retaining the original account access future.
     */
    @NotNull
    @Deprecated(forRemoval = true)
    @ApiStatus.ScheduledForRemoval(inVersion = "6.0.0")
    default CompletableFuture<@NotNull EconomyTransaction> setAsync(BigDecimal amount) {
        return CompletableFuture.completedFuture(this.set(amount));
    }

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
     * Asynchronously withdraws from the account's balance by the specified amount. This value should
     * conform to the specified limits denoted by the implementation. For instance, an account
     * may not be allowed to go beyond 0 and into the negatives, in which case, the implementation
     * should report a result of {@link EconomyResultType#NOT_ENOUGH_FUNDS}. Should any other
     * failure occur, {@link EconomyResultType#FAILED} or {@link EconomyResultType#CANCELLED}.
     *
     * <p>Unlike {@link #withdraw(BigDecimal)}, this method properly adheres to implementations which might
     * require stateful transactions to ensure data safety.</p>
     *
     * @return A completable future which reports an {@link EconomyTransaction} describing the result
     * of the operation
     * @since 5.1.0
     * @deprecated Early on, there were worries transactions would also need to be made async
     * to better support external stores. To better serve the API and thread usage, this design
     * is being deprecated in favor of retaining the original account access future.
     */
    @NotNull
    @Deprecated(forRemoval = true)
    @ApiStatus.ScheduledForRemoval(inVersion = "6.0.0")
    default CompletableFuture<@NotNull EconomyTransaction> withdrawAsync(BigDecimal amount) {
        return CompletableFuture.completedFuture(this.withdraw(amount));
    }

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
     * Asynchronously deposits the amount specified into the account. This value should conform to the
     * specified limits denoted by the implementation. For instance, an implementation might
     * impose max balance limits on an account. In the event such a restriction is imposed,
     * any use of this operation that would cause the account to push beyond that limit
     * will fail and be marked with a result of {@link EconomyResultType#NO_REMAINING_SPACE}.
     * Any other sort of failures will result in {@link EconomyResultType#FAILED} or
     * {@link EconomyResultType#CANCELLED}.
     *
     * <p>Unlike {@link #deposit(BigDecimal)}, this method properly adheres to implementations which might
     * require stateful transactions to ensure data safety.</p>
     *
     * @return A completable future which reports an {@link EconomyTransaction} describing the result
     * of the operation
     * @since 5.1.0
     * @deprecated Early on, there were worries transactions would also need to be made async
     * to better support external stores. To better serve the API and thread usage, this design
     * is being deprecated in favor of retaining the original account access future.
     */
    @NotNull
    @Deprecated(forRemoval = true)
    @ApiStatus.ScheduledForRemoval(inVersion = "6.0.0")
    default CompletableFuture<@NotNull EconomyTransaction> depositAsync(BigDecimal amount) {
        return CompletableFuture.completedFuture(this.deposit(amount));
    }

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
     * Asynchronously performs a bank transfer of the specified amount from this account to the target
     * account supplied as <code>to</code>. This effectively mirrors a withdrawal and deposit
     * interaction, validating both requirements before proceeding with the transfer.
     *
     * <p>Unlike {@link #transfer(Account, BigDecimal)}, this method properly adheres to implementations which might
     * require stateful transactions to ensure data safety.</p>
     *
     * @return A completable future which reports an {@link EconomyTransferTransaction} describing the result
     * of the operation
     * @since 5.1.0
     * @deprecated Early on, there were worries transactions would also need to be made async
     * to better support external stores. To better serve the API and thread usage, this design
     * is being deprecated in favor of retaining the original account access future.
     */
    @NotNull
    @Deprecated(forRemoval = true)
    @ApiStatus.ScheduledForRemoval(inVersion = "6.0.0")
    default CompletableFuture<@NotNull EconomyTransferTransaction> transferAsync(Account to, BigDecimal amount) {
        return CompletableFuture.completedFuture(this.transfer(to, amount));
    }

    /**
     * Resets an account's balance to the defined starting balance of the currency,
     * or a config's given override, if set.
     *
     * @return A transaction report indicating the result of the operation
     */
    @NotNull
    @CanIgnoreReturnValue
    EconomyTransaction reset();

    /**
     * Asynchronously resets an account's balance to the defined starting balance of the currency,
     * or a config's given override, if set.
     *
     * <p>Unlike {@link #transfer(Account, BigDecimal)}, this method properly adheres to implementations which might
     * require stateful transactions to ensure data safety.</p>
     *
     * @return A completable future which reports an {@link EconomyTransferTransaction} describing the result
     * of the operation
     * @since 5.1.0
     * @deprecated Early on, there were worries transactions would also need to be made async
     * to better support external stores. To better serve the API and thread usage, this design
     * is being deprecated in favor of retaining the original account access future.
     */
    @NotNull
    @Deprecated(forRemoval = true)
    @ApiStatus.ScheduledForRemoval(inVersion = "6.0.0")
    default CompletableFuture<@NotNull EconomyTransaction> resetAsync() {
        return CompletableFuture.completedFuture(this.reset());
    }

    /**
     * Most implementations should not really make use of this,
     *
     * @return A builder for accounts
     * @since 5.1.1
     */
    static AccountBuilder builder() {
        return Impactor.instance().builders().provide(AccountBuilder.class);
    }

    interface AccountBuilder extends Builder<Account> {

        /**
         * Specifies the currency this account should reflect. This option is optional and if
         * not specified, will default to the primary currency as specified by the economy service.
         *
         * @param currency The currency this account will be based on
         * @return This builder
         */
        @NotNull
        @Contract("_ -> this")
        @CanIgnoreReturnValue
        AccountBuilder currency(final @NotNull Currency currency);

        /**
         * Specifies the owner of the account. This need not be a player, but rather any identifiable
         * instance identified by a UUID. If the uuid does not link to a player, it should be marked
         * with {@link #virtual()}.
         *
         * @param uuid The UUID of the account owner
         * @return This builder
         */
        @NotNull
        @Contract("_ -> this")
        @CanIgnoreReturnValue
        AccountBuilder owner(final @NotNull UUID uuid);

        /**
         * Sets the balance of this account. This field is optional and if not specified, reflects the starting
         * balance specified by the account's currency backing.
         *
         * @param balance The balance to set for the account
         * @return This builder
         */
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

        /**
         * Given an account builder, allows further modification that was not originally specified.
         *
         * @param builder The original builder before
         * @return The modified builder
         */
        AccountBuilder modify(AccountBuilder builder);

    }

}
