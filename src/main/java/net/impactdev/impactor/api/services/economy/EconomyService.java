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

package net.impactdev.impactor.api.services.economy;

import net.impactdev.impactor.api.services.Service;
import net.impactdev.impactor.api.services.economy.accounts.Account;
import net.impactdev.impactor.api.services.economy.accounts.AccountAccessor;
import net.impactdev.impactor.api.services.economy.accounts.AccountLinker;
import net.impactdev.impactor.api.services.economy.currency.Currency;
import net.impactdev.impactor.api.services.economy.currency.CurrencyProvider;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * This service provides an "async-in-mind" design for economy based operations. As most
 * if not all economy related actions are made for storage systems that involve system
 * IO, Impactor provides this service with typical functionality with this expectation.
 *
 * <p>This service's goal is aimed to support multiple types of currencies, while additionally
 * providing the means for an {@link AccountAccessor} to not necessarily be a player, but
 * perhaps a system bank instead.
 */
public interface EconomyService extends Service {

    /**
     * Specifies the default currency supplied by this service. This will typically be
     * decided by the implementation, and will stand as the only available currency
     * with implementations that support only one currency.
     *
     * @return The default and always available currency
     */
    CurrencyProvider currencies();

    /**
     * Obtains the account held by an account holder. In the event an account does not
     * exist for this particular holder for the given currency, one will instead be
     * created and bound to the account holder for later access.
     *
     * @param accessor The account accessor to query for an account
     * @param currency The currency the returned account should reflect
     * @return The stored account, or a new account reflecting the request
     */
    CompletableFuture<Account> account(AccountAccessor accessor, Currency currency);

    /**
     * Provides the known account holders for each and every account holder registered
     * with the economy service. Rather than the holders themselves, this creates references instead
     * to model and conform to account holders which may not be contextually available, and must be
     * loaded to obtain their information.
     *
     * @return A list of all references to each account holder registered with the economy service
     */
    CompletableFuture<List<AccountAccessor>> accessors();

}
