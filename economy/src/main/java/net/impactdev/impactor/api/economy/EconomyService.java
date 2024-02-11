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

package net.impactdev.impactor.api.economy;

import com.google.common.collect.Multimap;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import net.impactdev.impactor.api.Impactor;
import net.impactdev.impactor.api.services.Service;
import net.impactdev.impactor.api.economy.accounts.Account;
import net.impactdev.impactor.api.economy.currency.Currency;
import net.impactdev.impactor.api.economy.currency.CurrencyProvider;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * This service provides an "async-in-mind" design for economy based operations. As most
 * if not all economy related actions are made for storage systems that involve system
 * IO, Impactor provides this service with typical functionality with this expectation.
 */
public interface EconomyService extends Service {

    static EconomyService instance() {
        return Impactor.instance().services().provide(EconomyService.class);
    }

    /**
     * Specifies the default currency supplied by this service. This will typically be
     * decided by the implementation, and will stand as the only available currency
     * with implementations that support only one currency.
     *
     * @return The default and always available currency
     */
    CurrencyProvider currencies();

    /**
     * Checks via the service account cache, delegating to system I/O if not available, to validate
     * whether an account exists for the primary currency, as specified by {@link CurrencyProvider#primary()}.
     *
     * @param uuid The uuid of the account to query for
     * @return A future result indicating if an account was found
     */
    default CompletableFuture<Boolean> hasAccount(UUID uuid) {
        return this.hasAccount(this.currencies().primary(), uuid);
    }

    /**
     * Checks via the service account cache, delegating to system I/O if not available, to validate
     * whether an account exists for the given currency.
     *
     * @param currency The currency to query against
     * @param uuid The uuid of the account to query for
     * @return A future result indicating if an account was found
     */
    CompletableFuture<Boolean> hasAccount(Currency currency, UUID uuid);

    /**
     * Fetches or creates an account bound to the primary currency, as specified by
     * {@link CurrencyProvider#primary()} using the given UUID. This will first attempt
     * to locate a cached account, delegating to a load attempt if no account can be found
     * under the specified criteria. If no account is found, a new account will be generated
     * instead.
     *
     * @param uuid The UUID of the owner of the account.
     * @return The stored account, or a new account reflecting the request
     */
    default CompletableFuture<Account> account(UUID uuid) {
        return this.account(this.currencies().primary(), uuid);
    }

    /**
     * Fetches or creates an account bound to the primary currency, as specified by
     * {@link CurrencyProvider#primary()} using the given UUID. This will first attempt
     * to locate a cached account, delegating to a load attempt if no account can be found
     * under the specified criteria.
     *
     * <p>If no account can be located after exhausting both look up options, a new account
     * will be generated instead, making use of the given <code>modifier</code> to apply
     * changes to the account. For instance, this modifier allows you to mark an account
     * as virtual, which can be a key indicator for bank accounts or any account not
     * owned by a player.
     *
     * @param uuid The uuid of the account owner
     * @param modifier A property which supplies an account builder for further modification
     * @return The stored account, or a new account reflecting the request
     */
    default CompletableFuture<Account> account(UUID uuid, Account.AccountModifier modifier) {
        return this.account(this.currencies().primary(), uuid, modifier);
    }

    /**
     * Fetches or creates an account bound to the specified currency using the given UUID.
     * This will first attempt to locate a cached account, delegating to a load attempt if
     * no account can be found under the specified criteria. If no account is found, a new
     * account will be generated instead.
     *
     * @param currency The currency this account will be bound to
     * @param uuid The UUID of the owner of the account.
     * @return The stored account, or a new account reflecting the request
     */
    CompletableFuture<Account> account(Currency currency, UUID uuid);

    /**
     * Like {@link #account(Currency, UUID)}, this method will either fetch or create an
     * account using the given currency and uuid. In the event a creation effort is required
     * for the request, requests can make use of the given builder to apply additional properties
     * that might be necessary for an account.
     *
     * @param currency The currency this account should represent
     * @param uuid The uuid of the account owner
     * @param modifier A property which supplies an account builder for further modification
     * @return The stored account, or a new account reflecting the request
     */
    CompletableFuture<Account> account(Currency currency, UUID uuid, Account.AccountModifier modifier);

    /**
     * Provides an immutable Multimap of currencies to all accounts accessible via the economy provider.
     *
     * @return A map of all currencies to accounts
     */
    CompletableFuture<Multimap<Currency, Account>> accounts();

    /**
     * Provides an immutable collection of accounts in regard to a particular currency.
     *
     * @param currency The currency to filter on
     * @return An immutable collection of accounts
     * @see #accounts()
     */
    default CompletableFuture<Collection<Account>> accounts(Currency currency) {
        return this.accounts().thenApply(map -> map.get(currency));
    }

    /**
     * Using the given UUID, attempts to delete an account owned by the given UUID for the primary currency.
     * If you wish to delete an account bound to another currency, use {@link #deleteAccount(Currency, UUID)}
     * instead.
     *
     * @param uuid The target account owner's {@link UUID}
     * @return A future useful for indicating task completion
     */
    default CompletableFuture<Void> deleteAccount(UUID uuid) {
        return this.deleteAccount(this.currencies().primary(), uuid);
    }

    /**
     * Using the given currency and UUID, attempts to delete a matching account that satisfies the currency binding
     * and is owned by the given UUID.
     *
     * @param currency The specific currency an account should be deleted from
     * @param uuid The target account owner's {@link UUID}
     * @return A future useful for indicating task completion
     */
    CompletableFuture<Void> deleteAccount(Currency currency, UUID uuid);

    /**
     * Saves the given account.
     *
     * @param account The account to save
     */
    @CanIgnoreReturnValue
    CompletableFuture<Void> save(Account account);

}
