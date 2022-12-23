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

package net.impactdev.impactor.api.text.events;

import net.impactdev.impactor.api.events.ImpactorEvent;
import net.impactdev.impactor.api.text.placeholders.PlaceholderParser;
import net.kyori.adventure.key.Key;

import java.util.Map;

/**
 * Fired when Impactor is setting up the placeholder service. This permits a plugin to register
 * their own placeholders such that they'd then be capable of being parsed through the
 * service. A placeholder is registered using a key, to help indicate the source of the placeholder
 * while also allowing for multiple placeholders with the same name to exist, such that they don't
 * fit the same key.
 */
public interface RegisterPlaceholdersEvent extends ImpactorEvent {

    RegisterPlaceholdersEvent register(Key key, PlaceholderParser parser);

    RegisterPlaceholdersEvent registerAll(Map<Key, PlaceholderParser> parsers);

}
