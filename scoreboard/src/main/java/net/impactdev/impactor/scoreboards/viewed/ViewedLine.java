package net.impactdev.impactor.scoreboards.viewed;

import net.impactdev.impactor.scoreboards.lines.ScoreboardLine;
import net.kyori.adventure.text.Component;

public interface ViewedLine {

    ScoreboardLine delegate();

    Component text();

}
