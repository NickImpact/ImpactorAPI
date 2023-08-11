package net.impactdev.impactor.scoreboards.effects;

import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.util.HSVLike;

public final class FadeEffect extends ColorAlteringEffect {

    public static FadeEffect create(int steps, int gap, int start) {
        return new FadeEffect(steps, gap, start);
    }

    private final int steps;
    private final int gap;
    private int start;

    private int phase;

    private FadeEffect(int steps, int gap, int start) {
        this.steps = steps;
        this.gap = gap;
        this.phase = start;
    }

    @Override
    protected void advance() {
        this.phase = (this.phase + this.gap) % 360;
    }

    @Override
    protected TextColor color() {
        int hue = (this.phase + this.gap) % 360;
        int spacer = 360 / this.steps;

        HSVLike hsv = HSVLike.hsvLike((hue + (this.phase * spacer)) % 360.0F / 360.0F, 1.0F, 1.0F);
        return TextColor.color(hsv);
    }

    @Override
    public void step() {
        this.start++;
        this.phase = start;
    }
}
