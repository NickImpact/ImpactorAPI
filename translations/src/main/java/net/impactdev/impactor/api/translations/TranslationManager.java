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

package net.impactdev.impactor.api.translations;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import net.impactdev.impactor.api.Impactor;
import net.impactdev.impactor.api.text.TextProcessor;
import net.impactdev.impactor.api.translations.repository.TranslationRepository;
import net.impactdev.impactor.api.utility.builders.Builder;
import net.kyori.adventure.translation.Translator;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Set;
import java.util.function.Supplier;

public interface TranslationManager {

    static Locale parseLocale(String input) {
        return Translator.parseLocale(input);
    }

    void initialize();

    TranslationRepository repository();

    void reload();

    TextProcessor processor();

    Locale defaultLocale();

    Set<Locale> installed();

    Path root();

    static TranslationManagerBuilder builder() {
        return Impactor.instance().builders().provide(TranslationManagerBuilder.class);
    }

    interface TranslationManagerBuilder extends Builder<TranslationManager> {

        @Contract("_ -> this")
        @CanIgnoreReturnValue
        TranslationManagerBuilder repository(final @NotNull TranslationRepository repository);

        @Contract("_ -> this")
        @CanIgnoreReturnValue
        TranslationManagerBuilder defaultLocale(final @NotNull Locale locale);

        @Contract("_ -> this")
        @CanIgnoreReturnValue
        TranslationManagerBuilder path(final @NotNull Path path);

        @Contract("_ -> this")
        @CanIgnoreReturnValue
        TranslationManagerBuilder processor(final @NotNull TextProcessor processor);

        @Contract("_ -> this")
        @CanIgnoreReturnValue
        TranslationManagerBuilder provided(final @NotNull Supplier<InputStream> supplier);

    }
}
