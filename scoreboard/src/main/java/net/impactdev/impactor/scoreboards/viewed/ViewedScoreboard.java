package net.impactdev.impactor.scoreboards.viewed;

import net.impactdev.impactor.api.Impactor;
import net.impactdev.impactor.api.platform.players.PlatformPlayer;
import net.impactdev.impactor.scoreboards.Scoreboard;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ViewedScoreboard {

    static ViewedScoreboard create(final @NotNull Scoreboard parent, final @NotNull PlatformPlayer viewer) {
        return Impactor.instance().factories().provide(Factory.class).create(parent, viewer);
    }

    Scoreboard delegate();

    PlatformPlayer viewer();

    ViewedObjective objective();

    List<ViewedLine> lines();

    void open();

    void hide();

    void destroy();

    interface Factory {

        ViewedScoreboard create(final @NotNull Scoreboard parent, final @NotNull PlatformPlayer viewer);

    }

}
