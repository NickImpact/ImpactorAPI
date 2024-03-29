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

package net.impactdev.impactor.api.economy.transactions.composer;

import com.google.common.base.Suppliers;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import net.impactdev.impactor.api.economy.accounts.Account;
import net.impactdev.impactor.api.economy.transactions.EconomyTransferTransaction;
import net.impactdev.impactor.api.economy.transactions.details.EconomyResultType;
import net.impactdev.impactor.api.utility.builders.Builder;
import net.impactdev.impactor.api.utility.builders.Required;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 *
 */
public interface TransferComposer extends Builder<EconomyTransferTransaction> {

    /**
     * Specifies the account this transaction should withdraw funds from
     *
     * @param account The account money is being taken from
     * @return This composer
     */
    @Required
    @Contract("_ -> this")
    @CanIgnoreReturnValue
    TransferComposer from(final @NotNull Account account);

    /**
     * Specifies the account this transaction should deposit funds into
     *
     * @param account The account money is being given to
     * @return This composer
     */
    @Required
    @Contract("_ -> this")
    @CanIgnoreReturnValue
    TransferComposer to(final @NotNull Account account);

    /**
     * Specifies the amount of currency to apply to the transaction.
     *
     * @param amount The amount of currency to manipulate
     * @return This composer
     */
    @Required
    @Contract("_ -> this")
    @CanIgnoreReturnValue
    TransferComposer amount(final @NotNull BigDecimal amount);

    /**
     * Binds a message to a particular result type. If a transaction completes with a binding in place, this
     * method will supply the transaction with that particular message.
     *
     * @param type The result type to bind to
     * @param message The message to set if the transaction completes with that result option
     * @return This composer
     */
    @Contract("_,_ -> this")
    @CanIgnoreReturnValue
    default TransferComposer message(final @NotNull EconomyResultType type, final @NotNull Component message) {
        return this.message(type, Suppliers.memoize(() -> message));
    }

    /**
     * Binds a message to a particular result type. If a transaction completes with a binding in place, this
     * method will supply the transaction with that particular message.
     *
     * @param type The result type to bind to
     * @param message The message to set if the transaction completes with that result option
     * @return This composer
     */
    @Contract("_,_ -> this")
    @CanIgnoreReturnValue
    TransferComposer message(final @NotNull EconomyResultType type, final @NotNull Supplier<@NotNull Component> message);

    /**
     * Using the provided transaction details, executes the composed transaction against the economy service.
     *
     * @return The transaction response detailing how the transaction applied
     */
    @Override
    EconomyTransferTransaction build();

    @NotNull
    @Deprecated
    @ApiStatus.ScheduledForRemoval(inVersion = "6.0.0")
    default CompletableFuture<@NotNull EconomyTransferTransaction> send() {
        return CompletableFuture.completedFuture(this.build());
    }

}
