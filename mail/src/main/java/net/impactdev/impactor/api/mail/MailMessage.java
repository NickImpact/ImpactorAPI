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

package net.impactdev.impactor.api.mail;

import net.kyori.adventure.text.Component;
import net.kyori.examination.Examinable;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface MailMessage {

    /**
     * Represents a personal unique identifier in an effort to aid in message locating. This field
     * is effectively extra metadata, and serves really only one true purpose.
     *
     * @return
     */
    UUID uuid();

    /**
     * Indicates the source of the message by their UUID, or {@link Optional#empty()} should the
     * source not be a player.
     *
     * @return The source of this message
     */
    Optional<UUID> source();

    /**
     * The actual content of this message. The message supports all sorts of formatting, but may
     * experience loss for contents that might not be as easily serializable, such as hover and click
     * events.
     *
     * @return The content of the message
     */
    Component content();

    /**
     * The immediate moment this message was generated and sent to the target's inbox.
     *
     * @return The Instant this message was generated and sent
     */
    Instant timestamp();

}
