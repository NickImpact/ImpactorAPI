package net.impactdev.impactor.scoreboards;

import net.impactdev.impactor.api.platform.players.PlatformPlayer;
import net.impactdev.impactor.scoreboards.lines.ScoreboardLine;
import net.impactdev.impactor.scoreboards.objectives.ScoreboardObjective;

public interface ScoreboardImplementation {

    void objective(PlatformPlayer viewer, ScoreboardObjective objective);

    void line(PlatformPlayer viewer, ScoreboardLine line);

    void show(PlatformPlayer viewer, Scoreboard scoreboard);

    void hide(PlatformPlayer viewer, Scoreboard scoreboard);

    void registerTeam(PlatformPlayer viewer);

    interface Factory {

        ScoreboardImplementation packets();

    }

}
