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

package net.impactdev.impactor.api.scoreboards.score;

import net.impactdev.impactor.api.annotations.Minecraft;
import net.impactdev.impactor.api.utility.builders.Builder;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Nullable;

import java.util.function.IntBinaryOperator;
import java.util.function.IntUnaryOperator;

/**
 * Represents an integer value that is used to represent the score of a Team. Scores are effectively mutable
 * integer values that track some sort of value by an entity. The criteria that denotes what a score is for
 * is justified by the Objective of a Scoreboard, versus the score itself.
 * <p>
 * <h2>Minecraft 1.20.3</h2>
 * As of Minecraft 1.20.3, scores now accept additional formatting relative to the integer value. This allows
 * for tremendously dynamic replacement of the integer values by replacing their display with something else
 * entirely, or even nothing at all!
 *
 * <p>
 * With this change, however, comes an important note. When viewed on a Scoreboard, individual scores are still
 * represented by the internal value of the score, from highest to lowest. While we may be able to change the
 * display value of a score, we should still maintain the internal value of the score in order to preserve
 * proper sorting, where necessary.
 */
public interface Score {

    /**
     * Represents the actual score as an integer value. This is the default display criteria for a scoreboard
     * when focusing on a particular line, and will control the position of a row on the scoreboard.
     *
     * @return The integer value representing this particular score
     * @since 5.2.0
     */
    int value();

    /**
     * Indicates how a score will be displayed on the scoreboard.
     *
     * <p>This particular feature requires Minecraft 1.20.3. It serves no functionality otherwise.</p>
     *
     * @return The formatter responsible for formatting the integer value representing this score
     * @since 5.2.0
     */
    @Nullable
    @Minecraft("1.20.3")
    ScoreFormatter formatter();

    /**
     * Specifies if the score is currently locked from being updated further.
     *
     * @return <code>true</code> if locked, <code>false</code> otherwise
     * @since 5.2.0
     */
    boolean locked();

    /**
     * If not already, locks the score from further updates. This effectively leaves the score in an immutable
     * state, until later unlocked.
     *
     * @since 5.2.0
     */
    void lock();

    /**
     * If not already, unlocks the score to allow updating of the interval score value. This leaves the score
     * in a mutable state until locked.
     *
     * @since 5.2.0
     */
    void unlock();

    /**
     * Updates the current score value using the internal value as the source input. This option allows for dynamic
     * updates based on the current score. This logic can otherwise be repeated with {@link #set(int)}, but this
     * option shorthands operations where setting the score requires a callback to {@link #value()}.
     *
     * <p>This call requires the score to be in an unlocked state for the score to apply.</p>
     *
     * @param operator The operator that will update the score
     * @since 5.2.0
     * @return <code>true</code> if the value was accepted, <code>false</code> otherwise
     */
    boolean update(IntUnaryOperator operator);

    /**
     * Sets the current score to the specified value. This call requires the score to be in an unlocked state
     * for the score to apply.
     *
     * @param value The value to set the internal value of this score to
     * @return <code>true</code> if the value was accepted, <code>false</code> otherwise
     */
    boolean set(int value);

    /**
     * Represents a builder capable of building a score.
     */
    interface ScoreBuilder extends Builder<Score> {

        /**
         * Sets the internal value of the score to the given integer value.
         *
         * @param score The actual value of the score
         * @return This builder
         */
        ScoreBuilder score(int score);

        /**
         * Sets the formatter that will be used to display the internal score value.
         *
         * <p>This particular feature requires Minecraft 1.20.3. It serves no functionality otherwise.</p>
         *
         * @param formatter A formatter used to decorate or replace the internal score value
         * @return This builder
         */
        @Minecraft("1.20.3")
        ScoreBuilder formatter(ScoreFormatter formatter);

        /**
         * Specifies if the score should be locked. By default, Impactor will set this value to be
         * unlocked for dynamic replacements. Should you wish to lock the score immediately, use
         * this method with a value of <code>true</code>.
         *
         * @param state The state this score should be in
         * @return This builder
         */
        ScoreBuilder locked(boolean state);

    }

}
