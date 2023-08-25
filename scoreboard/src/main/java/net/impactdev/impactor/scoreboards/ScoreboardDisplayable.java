package net.impactdev.impactor.scoreboards;

import net.impactdev.impactor.scoreboards.updaters.ComponentResolver;
import net.kyori.adventure.text.Component;

import java.util.function.Supplier;

public interface ScoreboardDisplayable {

    Supplier<Component> provider();

    ComponentResolver resolver();

}
