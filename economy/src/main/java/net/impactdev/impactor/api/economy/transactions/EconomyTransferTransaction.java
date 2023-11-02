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

package net.impactdev.impactor.api.economy.transactions;

import net.impactdev.impactor.api.Impactor;
import net.impactdev.impactor.api.economy.accounts.Account;
import net.impactdev.impactor.api.economy.currency.Currency;
import net.impactdev.impactor.api.economy.transactions.composer.TransferComposer;
import net.impactdev.impactor.api.economy.transactions.details.EconomyResultType;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.function.Supplier;

public interface EconomyTransferTransaction {

    /**
     * Creates a new composer action which allows a third party to execute transactions against the economy service
     * without directly working within an account. The main usage for this type of transaction is meant to allow
     * a caller to feed the transaction custom success/error messages for any possible result from the transaction.
     *
     * <p>
     * For instance, you can use the composer to register a {@link Component} bound to {@link EconomyResultType#SUCCESS}.
     * If the transaction completes with a successful result type, the message can then be accessed via {@link #message()}
     * or even directly sent to an audience via {@link #inform(Audience)}.
     *
     * @return A new composer capable of firing off a transaction
     */
    static TransferComposer compose() {
        return Impactor.instance().builders().provide(TransferComposer.class);
    }

    /**
     * The currency used within the transaction.
     *
     * @return The currency for the transaction
     */
    Currency currency();

    /**
     * Indicates the {@link Account} from which currency is being withdrawn.
     *
     * @return The account being withdrawn from
     */
    Account from();

    /**
     * Indicates the {@link Account} to which currency is being deposited.
     *
     * @return The target being deposited to
     */
    Account to();

    /**
     * The amount of currency involved within this transaction.
     *
     * @return The total amount processed by a transaction
     */
    BigDecimal amount();

    /**
     * The result of the transaction, indicating either success or some indication of failure
     * for why a transaction failed to be processed.
     *
     * @return The result of the transaction
     */
    EconomyResultType result();

    /**
     * Specifies if the given transaction has completed successfully, as indicated by the result of
     * {@link #result()}. Should a transaction complete with any status outside of {@link EconomyResultType#SUCCESS},
     * this call will result in a <code>false</code> return value.
     *
     * @return <code>true</code> when the transaction completed successfully, <code>false</code> otherwise
     */
    default boolean successful() {
        return this.result() == EconomyResultType.SUCCESS;
    }

    /**
     * Specifies a message that is used to help indicate the result status of a transaction. This message will
     * not necessarily always be available, and is really ever only populated when a transaction is proposed via
     * a {@link TransferComposer}.
     *
     * @return A component indicating a human-readable result status, or null if not bound
     */
    @Nullable Supplier<Component> message();

    /**
     * If a message representing the transaction's result status is bound and populated, this will inform
     * a specified audience with the specific message.
     *
     * @param audience The audience to send the transaction status to
     */
    default void inform(Audience audience) {
        Supplier<Component> message = this.message();
        if(message != null) {
            audience.sendMessage(message.get());
        }
    }

}
