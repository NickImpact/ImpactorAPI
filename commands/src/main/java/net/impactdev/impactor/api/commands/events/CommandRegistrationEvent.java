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

import cloud.commandframework.Command;
import cloud.commandframework.meta.CommandMeta;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import net.impactdev.impactor.api.commands.CommandSource;
import net.impactdev.impactor.api.events.ImpactorEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Fired once the game platform indicates commands are being registered.
 */
public interface CommandRegistrationEvent extends ImpactorEvent {

    /**
     * Allows for registration of commands that a listener would provide. These commands
     * are built using the <a href="https://github.com/Incendo/cloud/tree/master/docs">
     * Cloud Command Framework</a> logic, in an effort to maintain platform-agnostic
     * capabilities.
     *
     * @param name The literal name of the command
     * @param meta Metadata detailing the purpose of the command
     * @param provider A provider which is responsible for building a command from a provided default builder
     * @param aliases Additional keys which might map to the command
     * @return This event for registration chaining
     */
    @Contract("_,_,_,_ -> this")
    @CanIgnoreReturnValue
    CommandRegistrationEvent register(String name, CommandMeta meta, CommandProvider provider, String... aliases);

    /**
     * Attempts to parse a class for dynamic command construction. This process effectively follows the annotation processor policy
     * detailed via <a href="https://github.com/Incendo/cloud/tree/master/docs#42-annotation-parser">Cloud documentation</a>,
     * but without exposing the internal annotation processor.
     *
     * <p>Unlike {@link #register(String, CommandMeta, CommandProvider, String...)}  the other register option}, this call
     * allows for method declaration of a command tree without a builder. However, it does come packed with an additional
     * set of exceptions.
     *
     * @param container The class representing a command container
     * @return This event for registration chaining
     */
    @Contract("_ -> this")
    @CanIgnoreReturnValue
    CommandRegistrationEvent register(final @NotNull Class<?> container);

    @FunctionalInterface
    interface CommandProvider {

        Command<CommandSource> construct(Command.Builder<CommandSource> builder);

    }

}
