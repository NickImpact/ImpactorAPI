package net.impactdev.impactor.scoreboards.effects;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.jetbrains.annotations.NotNull;

import java.util.PrimitiveIterator;

public abstract class ColorAlteringEffect implements TextEffect {

    @Override
    public final Component transform(@NotNull Component input) {
        if(input instanceof TextComponent) {
            TextComponent parent = (TextComponent) input;
            if(input.style().color() != null) {
                return input;
            }

            String content = parent.content();
            if(content.length() > 0) {
                final TextComponent.Builder builder = Component.text();

                final int[] holder = new int[1];
                for (final PrimitiveIterator.OfInt it = content.codePoints().iterator(); it.hasNext();) {
                    holder[0] = it.nextInt();
                    final Component comp = Component.text(new String(holder, 0, 1), input.style().color(this.color()));
                    this.advance();
                    builder.append(comp);
                }

                return builder.build();
            }
        }

        return input;
    }

    protected abstract void advance();

    protected abstract TextColor color();

}
