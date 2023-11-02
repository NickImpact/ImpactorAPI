package net.impactdev.impactor.api.relativity;

import net.impactdev.impactor.api.platform.sources.PlatformSource;
import net.kyori.adventure.text.Component;

@FunctionalInterface
public interface RelativeParser {

    Component parse(PlatformSource viewer);

}
