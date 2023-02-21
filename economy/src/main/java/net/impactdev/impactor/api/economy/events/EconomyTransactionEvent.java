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

package net.impactdev.impactor.api.economy.events;

import net.impactdev.impactor.api.economy.accounts.Account;
import net.impactdev.impactor.api.economy.currency.Currency;
import net.impactdev.impactor.api.economy.transactions.EconomyTransaction;
import net.impactdev.impactor.api.economy.transactions.EconomyTransactionType;
import net.impactdev.impactor.api.events.ImpactorEvent;
import net.kyori.event.Cancellable;

import java.math.BigDecimal;

/**
 * Represents an event used to track transactions made through the economy service.
 * This can be used for logging or monitoring transactions as soon as they are made
 * and detected.
 */
public interface EconomyTransactionEvent {

    /**
     * Represents the currency involved in the transaction.
     *
     * @return The currency as specified by the transaction.
     */
    Currency currency();

    /**
     * Represents the account involved in the transaction. This is the account
     * that is being affected by the transaction.
     *
     * @return The account as specified by the transaction.
     */
    Account account();

    /**
     * Represents the event which transpires just before a transaction is enacted. This can
     * be used as a means of blocking a transaction should it become necessary.
     */
    interface Pre extends EconomyTransactionEvent, ImpactorEvent, Cancellable {

        /**
         * The amount of currency involved within the transaction.
         *
         * @return The total amount processed by a transaction
         */
        BigDecimal amount();

        /**
         * Indicates the type of transaction taking place. This is expected normally
         * to be your usual deposit, withdraw, or transfer actions.
         *
         * @return The transaction type being performed
         */
        EconomyTransactionType type();

    }

    /**
     * Represents the event which transpires just after a transaction has occurred. By nature,
     * this event is simply a summary of the transaction and cannot be cancelled. In the event
     * the corresponding {@link Pre} event is cancelled, a Post event will not be generated.
     */
    interface Post extends EconomyTransactionEvent, ImpactorEvent {

        @Override
        default Currency currency() {
            return this.transaction().currency();
        }

        @Override
        default Account account() {
            return this.transaction().account();
        }

        /**
         * Represents the tracked transaction being made during this event cycle. This should
         * contain information regarding the result of the transaction, how much was processed,
         * and what type of transaction was made.
         *
         * @return A transaction captured by this event
         */
        EconomyTransaction transaction();

    }

}
