package net.impactdev.impactor.scoreboards.viewed;

import net.impactdev.impactor.scoreboards.objectives.Objective;
import net.kyori.adventure.text.Component;

public interface ViewedObjective {

    Objective delegate();

    Component text();

}
