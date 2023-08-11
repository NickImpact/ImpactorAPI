package net.impactdev.impactor.scoreboards.effects;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public interface TextEffect {

    Component transform(final @NotNull Component input);

    void step();

}
