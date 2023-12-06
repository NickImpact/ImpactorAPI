/*
 * This file is part of Impactor, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2018-2022 NickImpact
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 */

package net.impactdev.impactor.api.scoreboards.display.resolvers.text;

import net.impactdev.impactor.api.Impactor;
import net.impactdev.impactor.api.platform.sources.PlatformSource;
import net.impactdev.impactor.api.utility.Context;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.jetbrains.annotations.Contract;

import java.util.List;

public interface ScoreboardComponent {

    Component resolve(PlatformSource viewer, Context context);

    @Contract(pure = true)
    ScoreboardComponent append(ComponentElement element);

    List<ComponentElement> elements();

    static ScoreboardComponent create(ComponentElement root) {
        return Impactor.instance().factories().provide(Factory.class).create(root);
    }

    static ScoreboardComponent create(ComponentLike component) {
        return create(ComponentElement.create((viewer, context) -> component.asComponent()));
    }

    interface Factory {

        ScoreboardComponent create(ComponentElement root);

    }

}
