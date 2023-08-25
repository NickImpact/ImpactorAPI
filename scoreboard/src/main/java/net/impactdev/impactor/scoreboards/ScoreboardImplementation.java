package net.impactdev.impactor.scoreboards;

import net.impactdev.impactor.api.Impactor;
import net.impactdev.impactor.api.platform.players.PlatformPlayer;
import net.impactdev.impactor.scoreboards.lines.ScoreboardLine;
import net.impactdev.impactor.scoreboards.objectives.Objective;

public interface ScoreboardImplementation {

    void objective(PlatformPlayer viewer, Objective objective);

    void line(PlatformPlayer viewer, ScoreboardLine line);

    void show(PlatformPlayer viewer, Scoreboard scoreboard);

    void hide(PlatformPlayer viewer, Scoreboard scoreboard);

    void registerTeam(PlatformPlayer viewer);

    static ScoreboardImplementation packets() {
        return Impactor.instance().factories().provide(Factory.class).packets();
    }

    interface Factory {

        ScoreboardImplementation packets();

    }

}
