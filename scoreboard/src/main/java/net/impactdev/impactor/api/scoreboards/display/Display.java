package net.impactdev.impactor.api.scoreboards.display;

import net.impactdev.impactor.api.scoreboards.display.resolvers.ComponentResolver;
import net.kyori.adventure.text.Component;

public interface Display {

    ComponentResolver resolver();

    void resolve();

    Component text();

}
