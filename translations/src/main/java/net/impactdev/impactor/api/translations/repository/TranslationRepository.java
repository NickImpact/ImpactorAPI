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

package net.impactdev.impactor.api.translations.repository;

import net.impactdev.impactor.api.Impactor;
import net.impactdev.impactor.api.translations.metadata.LanguageInfo;
import net.impactdev.impactor.api.utility.builders.Builder;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public interface TranslationRepository {

    /**
     * Fetches a set of currently available languages that apply to the hosting
     * translation manager. This will make a web-based request to a particular endpoint
     * to fulfill the given request.
     *
     * @return A future which contains the set of languages available to the translation
     * manager.
     */
    CompletableFuture<Set<LanguageInfo>> available();

    /**
     * Attempts to refresh the current translation set such that given rules apply.
     * If any rule fails validation, the refresh attempt will be marked invalid and
     * no action will commence.
     *
     * @return A future which will process the refresh request, and indicate whether
     * a refresh successfully occurred.
     */
    CompletableFuture<Boolean> refresh();

    /**
     * Downloads the latest translations from the target download endpoint. Information about the
     * download will be forwarded to an empty audience when running this method, so details
     * about the transaction will not be forwarded to an end user. This rule is however broken
     * in the event a failure occurs for a particular language, in which an exception will be
     * sent to the server.
     *
     * @param languages The set of languages to download and install
     * @param update Whether this request should update the last refresh status file
     * @return A future responsible for enacting the transaction
     */
    default CompletableFuture<Void> downloadAndInstall(final @NotNull Set<LanguageInfo> languages, final boolean update) {
        return this.downloadAndInstall(languages, Audience.empty(), update);
    }

    /**
     * Downloads the latest translations from the target download endpoint. Information about the
     * download will be forwarded to the target audience in regard to progress made by the download
     * process. Once complete, the translation manager hosting this particular repository will
     * be forcibly reloaded to adhere to the new translation set.
     *
     * @param languages The set of languages to download and install
     * @param audience The audience information should be forwarded to
     * @param update Whether this request should update the last refresh status file
     * @return A future responsible for enacting the transaction
     */
    CompletableFuture<Void> downloadAndInstall(final @NotNull Set<LanguageInfo> languages, final @NotNull Audience audience, final boolean update);

    static RepositoryBuilder builder() {
        return Impactor.instance().builders().provide(RepositoryBuilder.class);
    }

    interface RepositoryBuilder extends Builder<TranslationRepository> {

        RepositoryBuilder endpoint(final @NotNull TranslationEndpoint endpoint, final @NotNull String baseURL);

        RepositoryBuilder refreshRule(final @NotNull Supplier<Boolean> rule);

        RepositoryBuilder maxBundleSize(final long size);

        RepositoryBuilder maxCacheAge(final long age);

    }
}
