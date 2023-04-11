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

package net.impactdev.impactor.api.translations.metadata;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.impactdev.impactor.api.translations.TranslationManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public final class LanguageInfo {

    private final String id;
    private final String name;
    private final Locale locale;
    private final int progress;
    private final List<String> contributors;

    public LanguageInfo(String id, JsonObject data) {
        this.id = id;
        this.name = data.get("name").getAsString();
        this.locale = Objects.requireNonNull(TranslationManager.parseLocale(data.get("id").getAsString()));
        this.progress = data.get("progress").getAsInt();
        this.contributors = new ArrayList<>();
        for (JsonElement contributor : data.get("contributors").getAsJsonArray()) {
            this.contributors.add(contributor.getAsJsonObject().get("name").getAsString());
        }
    }

    public String id() {
        return this.id;
    }

    public String name() {
        return this.name;
    }

    public Locale locale() {
        return this.locale;
    }

    public int progress() {
        return this.progress;
    }

    public List<String> contributors() {
        return this.contributors;
    }

}
