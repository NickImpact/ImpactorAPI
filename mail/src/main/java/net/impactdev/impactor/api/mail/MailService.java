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

import net.impactdev.impactor.api.mail.filters.MailFilter;
import net.impactdev.impactor.api.platform.sources.PlatformSource;
import net.impactdev.impactor.api.services.Service;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.util.TriState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface MailService extends Service {

    /**
     * Fetches all mail addressed to the given target. Depending on the implementing service or storage,
     * this might make calls to external databases or other forms of data stores. As such, it is expected
     * that results might not immediately be available.
     *
     * @param target The UUID of the target
     * @return A list of mail, indicating the message alongside additional metadata
     */
    CompletableFuture<List<MailMessage>> inbox(final @NotNull UUID target);

    /**
     * Sends a message from the server to the given target with the following message. This message is expected
     * to be sent as raw text, receiving no special formatting, unless otherwise specified by the implementation.
     *
     * @param target The UUID of the target
     * @param message A set of simple text, unless otherwise specified.
     * @return A future wrapping the task of sending the message
     */
    default CompletableFuture<Boolean> sendFromServer(final @NotNull UUID target, final @NotNull String message) {
        return this.sendFromServer(target, Component.text(message));
    }

    /**
     * Sends a message from the server to the given target with the following message. Note that the message
     * is expected to experience a loss of certain metadata, due to the need to be serialized and deserialized.
     * Expected loss would be things like click and hover events.
     *
     * @param target The UUID of the target
     * @param message A set of simple text, unless otherwise specified.
     * @return A future wrapping the task of sending the message
     */
    CompletableFuture<Boolean> sendFromServer(final @NotNull UUID target, final @NotNull Component message);

    /**
     * Sends a raw message, unless otherwise specified, to the target from the source.
     *
     * @param source The source that this message originated from
     * @param target The target that will receive this message
     * @param message The message contents, in a raw standard
     * @return A future wrapping the task of sending the message
     */
    default CompletableFuture<Boolean> send(final @NotNull UUID source, final @NotNull UUID target, final @NotNull String message) {
        return this.send(source, target, Component.text(message));
    }

    /**
     * Sends a message to the target from the source. Note that the message is expected to experience a loss
     * of certain metadata, due to the need to be serialized and deserialized. Expected loss would be things
     * like click and hover events.
     *
     * @param source The source that this message originated from
     * @param target The target that will receive this message
     * @param message The message contents, in a raw standard
     * @return A future wrapping the task of sending the message
     */
    CompletableFuture<Boolean> send(final @NotNull UUID source, final @NotNull UUID target, final @NotNull Component message);

    /**
     * Sends a pre-generated message to the target. Details such as source and message content should be provided
     * as a part of the constructed message.
     *
     * @param target The user receiving this message
     * @param message The message to be sent to the target
     * @return A future wrapping the task of sending the message
     */
    CompletableFuture<Boolean> send(final @NotNull UUID target, final @NotNull MailMessage message);

    /**
     * Deletes a message from the inbox of the target.
     *
     * @param target The uuid of the target
     * @param message The message we are attempting to delete
     * @return A future indicating the result of the request, where:
     * <ul>
     *     <li>{@link TriState#TRUE} = Success</li>
     *     <li>{@link TriState#FALSE} = Failed</li>
     *     <li>{@link TriState#NOT_SET} = Message not found</li>
     * </ul>
     * */
    CompletableFuture<TriState> delete(final @NotNull UUID target, final @NotNull MailMessage message);

    /**
     * Clears out a user's entire inbox, such that every message is deleted. Should you wish for
     * finder control, supply a {@link MailFilter}.
     *
     * @param target The target to have their inbox cleared
     * @return A future indicating the result of the request, where:
     * <ul>
     *     <li>{@link TriState#TRUE} = Success</li>
     *     <li>{@link TriState#FALSE} = Failed</li>
     *     <li>{@link TriState#NOT_SET} = Empty Inbox</li>
     * </ul>
     * @see #deleteWhere(UUID, MailFilter) (UUID, MailFilter)
     */
    default CompletableFuture<TriState> deleteAll(final @NotNull UUID target) {
        return this.deleteWhere(target, null);
    }

    /**
     * Clears the inbox of a user, using the given filter. Should the filter be null, this will consider
     * all messages valid for deletion.
     *
     * <p>NOTE: This method is expected to be more expensive due to the filters necessary and how some
     * storage methods might not be indexed to accommodate the filters.</p>
     *
     * @param target The target to have their inbox cleared
     * @param filter A filter used to state which messages ought to be cleared
     * @return A future indicating the result of the request, where:
     * <ul>
     *     <li>{@link TriState#TRUE} = Success</li>
     *     <li>{@link TriState#FALSE} = Failed</li>
     *     <li>{@link TriState#NOT_SET} = Empty Inbox</li>
     * </ul>
     */
    CompletableFuture<TriState> deleteWhere(final @NotNull UUID target, final @Nullable MailFilter filter);

}
