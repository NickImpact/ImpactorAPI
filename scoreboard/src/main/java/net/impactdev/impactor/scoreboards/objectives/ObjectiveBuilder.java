package net.impactdev.impactor.scoreboards.objectives;

import net.impactdev.impactor.api.utility.builders.Builder;
import net.impactdev.impactor.scoreboards.updaters.ComponentResolver;
import net.kyori.adventure.text.Component;

import java.util.function.Supplier;

public interface ObjectiveBuilder extends Builder<Objective> {

    ObjectiveBuilder updater(ComponentResolver updater);

}
