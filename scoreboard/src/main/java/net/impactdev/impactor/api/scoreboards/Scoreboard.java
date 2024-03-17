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
import net.impactdev.impactor.api.utility.builders.Builder;
import net.impactdev.impactor.api.scoreboards.lines.ScoreboardLine;
import net.impactdev.impactor.api.scoreboards.objectives.Objective;
import net.kyori.examination.Examinable;

import java.util.List;

/**
 * <h2>Background</h2>
 * A scoreboard represents the minecraft interface that is normally used to track
 * scores across certain objectives for a set of entities or "teams". With this API,
 * we effectively hijack this feature to display custom content to our desires.
 *
 * <p></p>
 * <h2>Shortcomings</h2>
 * Due to the nature of scoreboards and their design, you are limited to 15 lines of
 * general content, plus one extra for the objective element. Additionally, it is
 * impossible to remove the red colored "scores" on the right side of each content
 * line.
 *
 * <p></p>
 * <h2>Capabilities</h2>
 * Scoreboards managed through impactor are capable of updating in many different ways.
 *
 */
public interface Scoreboard {

    static ScoreboardBuilder builder() {
        return Impactor.instance().builders().provide(ScoreboardBuilder.class);
    }

    /**
     * Represents the renderer responsible for displaying the scoreboard to the client. This should query
     * the respective elements of the scoreboard to get the currently available text, in the correct state
     * specified by the component resolver.
     *
     * @return The renderer used to display the scoreboard to the target client.
     * @since 5.2.0
     */
    ScoreboardRenderer renderer();

    /**
     * Represents the objective of a scoreboard, or otherwise the "title" element.
     *
     * @return The objective of the viewed scoreboard.
     * @since 5.2.0
     */
    Objective objective();

    /**
     * Represents the list of lines that would populate this scoreboard. Note that a scoreboard can only hold
     * up to 15 lines, excluding the objective (making the total count 16). When rendered to the client, only
     * the lines with the highest score will be capable of being displayed.
     *
     * @return An immutable list of lines that would be displayed on the scoreboard, sorted by score.
     * @since 5.2.0
     */
    List<ScoreboardLine> lines();

    /**
     * Creates a render-able version of this scoreboard for the target viewer. This will initialize all components
     * of the scoreboard in preparation to be viewed by the client, but will hold them in a state that is not yet
     * receiving updates. This also does not yet show the scoreboard to the client. To do so, consult
     * {@link AssignedScoreboard#open()}.
     *
     * @param viewer The player that will be viewing this particular scoreboard
     * @return A version of the scoreboard relative to the player.
     * @since 5.2.0
     */
    default AssignedScoreboard assignTo(PlatformPlayer viewer) {
        return AssignedScoreboard.create(this, viewer);
    }

    interface ScoreboardBuilder extends Builder<Scoreboard> {

        ScoreboardBuilder renderer(ScoreboardRenderer implementation);

        ScoreboardBuilder objective(Objective objective);

        ScoreboardBuilder line(ScoreboardLine line);

    }

}
