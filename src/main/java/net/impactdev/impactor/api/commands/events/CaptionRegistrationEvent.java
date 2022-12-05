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

package net.impactdev.impactor.api.commands.events;

import cloud.commandframework.captions.Caption;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import net.impactdev.impactor.api.events.ImpactorEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface CaptionRegistrationEvent extends ImpactorEvent {

    /**
     * Registers a caption used for argument parsing. A caption for the cloud command framework is
     * simply a registration key unique to the given format. The format should ideally contain
     * variables your argument parsers would fill in, using the given format: {variable}. For
     * example, on an invalid input of any kind, we could have our format be: "{input} is invalid",
     * where "{input}" would be supplied by the parser.
     *
     * @param key The caption to use for the particular format
     * @param format The format of the message to be sent to the command source
     * @return This event for registration chaining
     */
    @Contract("_,_ -> this")
    @CanIgnoreReturnValue
    CaptionRegistrationEvent register(final @NotNull Caption key, final @NotNull String format);

}
