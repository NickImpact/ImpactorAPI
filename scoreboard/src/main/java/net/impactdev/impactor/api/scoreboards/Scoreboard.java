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
import net.impactdev.impactor.api.scoreboards.relative.PlayerScoreboard;

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

    ScoreboardImplementation implementation();

    /**
     * Represents the objective of a scoreboard, or otherwise the "title" element.
     *
     * @return
     */
    Objective objective();

    List<ScoreboardLine> lines();

    default PlayerScoreboard createFor(PlatformPlayer viewer) {
        return PlayerScoreboard.create(this, viewer);
    }

    interface ScoreboardBuilder extends Builder<Scoreboard> {

        ScoreboardBuilder implementation(ScoreboardImplementation implementation);

        ScoreboardBuilder objective(Objective objective);

        ScoreboardBuilder line(ScoreboardLine line);

    }

}
