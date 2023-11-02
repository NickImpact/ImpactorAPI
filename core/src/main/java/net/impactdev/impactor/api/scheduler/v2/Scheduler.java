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

package net.impactdev.impactor.api.scheduler.v2;

import net.impactdev.impactor.api.scheduler.SchedulerTask;
import net.impactdev.impactor.api.scheduler.Ticks;
import net.kyori.adventure.key.Key;
import org.checkerframework.checker.index.qual.NonNegative;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Represents a system capable of running tasks set on a scheduled configuration. Schedulers running under this
 * implementation are basic in nature, and simply only consider delays and repeating intervals for task execution.
 * Scheduled tasks which seek to make use of scheduling systems like cron should consider additional alternatives.
 *
 * @since 5.2.0
 */
public interface Scheduler {

    Key SYNCHRONOUS = Key.key("impactor", "sync");
    Key ASYNCHRONOUS = Key.key("impactor", "async");

    /**
     * Represents the key that identifies this scheduler
     *
     * @return The scheduler's identifier
     */
    Key key();

    /**
     * Represents the executor this scheduler is executing on.
     *
     * @return The scheduler's executor
     */
    Executor executor();

    /**
     * Submits a task for execution on the next tick cycle of a scheduler. This sort of task
     * will only run once, with the only delay being the actual clock itself of the scheduler. As such,
     * this method provides no {@link SchedulerTask} as task cancellation is effectively redundant.
     *
     * @param action The action to execute against this scheduler
     */
    void publish(@NotNull Runnable action);

    /**
     * Submits a task for execution after waiting a set number of ticks. Schedulers can expect ticks
     * to effectively represent 50 milliseconds, making for an average of 20/second.
     *
     * @param action The action to execute
     * @param ticks The amount of ticks to wait before executing
     * @return A wrapper capable of cancelling the action before execution, if not yet executed
     */
    SchedulerTask delayed(@NotNull Consumer<Task> action, @NotNull Ticks ticks);

    /**
     * Submits a task for execution after waiting for the specified delay time. In the case of the
     * synchronous executor, this might be approximate, and not properly measure the
     *
     * @param action The action to execute
     * @param delay The amount of time to wait before executing
     * @param unit The time unit describing the amount of time to delay for
     * @return A wrapper capable of cancelling the action before execution, if not yet executed
     */
    SchedulerTask delayed(@NotNull Runnable action, @NonNegative long delay, @NotNull TimeUnit unit);

    /**
     * Submits a task to be executed per every set of ticks specified. Schedulers can expect ticks
     * to effectively represent 50 milliseconds, making for an average of 20/second.
     *
     * @param action The action to execute
     * @param ticks The amount of ticks specifying the execution interval
     * @return A wrapper capable of stopping the task from executing further
     */
    SchedulerTask repeating(@NotNull Runnable action, @NotNull Ticks ticks);

    /**
     * Submits a task to be executed on an interval, occurring at the rate specified by <code>interval</code>.
     *
     * @param action The action to execute
     * @param interval The amount of time to wait between executions
     * @param unit The time unit describing the amount of time to delay for
     * @return A wrapper capable of stopping the task from executing further
     */
    SchedulerTask repeating(@NotNull Runnable action, @NonNegative long interval, @NotNull TimeUnit unit);

    /**
     * Executes a task with an initial delay, then allowing the action to repeat infinitely based on the
     * given interval. The timings here are in ticks, and schedulers should assume 20 ticks is equal to 1 second,
     * or 1 tick equal to 50 milliseconds.
     *
     * @param action The action to execute
     * @param delay The initial delay before task execution
     * @param interval The rate at which this task will be executed
     * @return A wrapper capable of stopping the task from further execution
     */
    SchedulerTask delayedAndRepeating(@NotNull Runnable action, @NotNull Ticks delay, @NotNull Ticks interval);

    /**
     * Executes a task with an initial delay, then allowing the action to repeat infinitely based on the
     * given interval.
     *
     * @param action The action to execute
     * @param delay The initial delay before task execution
     * @param interval The rate at which this task will be executed
     * @param unit The time unit describing the amount of time to delay for
     * @return A wrapper capable of stopping the task from further execution
     */
    SchedulerTask delayedAndRepeating(@NotNull Runnable action, @NonNegative long delay, @NonNegative long interval, @NotNull TimeUnit unit);

    /**
     * Performs shutdown related tasks required by the scheduler. This should do any necessary clean up to ensure
     * task execution is properly halted.
     */
    void shutdown();

}
