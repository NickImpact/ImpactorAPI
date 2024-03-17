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

package net.impactdev.impactor.api.scoreboards;

import net.impactdev.impactor.api.Impactor;
import net.impactdev.impactor.api.platform.players.PlatformPlayer;
import net.impactdev.impactor.api.scoreboards.lines.ScoreboardLine;
import net.impactdev.impactor.api.scoreboards.objectives.Objective;
import net.impactdev.impactor.api.utility.pointers.PointerCapable;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * This class represents a scoreboard built from the global {@link Scoreboard} configuration, assigned to a particular
 * player. Elements of the scoreboard would then update for this player alone, allowing for relative deserialization
 *
 */
public interface AssignedScoreboard extends PointerCapable {

    static AssignedScoreboard create(final @NotNull Scoreboard parent, final @NotNull PlatformPlayer viewer) {
        return Impactor.instance().factories().provide(Factory.class).create(parent, viewer);
    }

    /**
     * Represents the scoreboard configuration used for this particular assigned scoreboard. This contains the definitions
     * for each component of the scoreboard, such as the title (objective) and lines with their individual scores.
     *
     * @return The configurable scoreboard to be used for rendering the assigned scoreboard
     */
    Scoreboard configuration();

    /**
     * The user assigned to this particular scoreboard, in which components would otherwise be relatively
     * resolved around as necessary
     *
     * @return The viewer of this scoreboard
     */
    PlatformPlayer viewer();

    /**
     * Represents the displayed version of the objective through the scoreboard configuration. This instance maintains
     * the running tasks assigned to the configuration, and is responsible for updating elements to itself only.
     *
     * @return The displayed version of the configured objective
     */
    Objective.Displayed objective();

    /**
     * Represents the list of displayed scoreboard lines built from the scoreboard configuration. Each instance
     * maintains their running tasks assigned via the configuration, and is responsible for updating elements to
     * itself only.
     *
     * @return The displayed version of the configured lines
     */
    List<ScoreboardLine.Displayed> lines();

    /**
     * Opens the scoreboard to the target, if not open already.
     */
    void open();

    /**
     * Hides the scoreboard from the target if not already visible.
     */
    void hide();

    /**
     * Responsible for destroying all elements of the scoreboard when being cleaned up. In other words, this
     * should tell each displayable to cancel its running tasks.
     */
    void destroy();

    interface Factory {

        AssignedScoreboard create(final @NotNull Scoreboard parent, final @NotNull PlatformPlayer viewer);

    }

}
