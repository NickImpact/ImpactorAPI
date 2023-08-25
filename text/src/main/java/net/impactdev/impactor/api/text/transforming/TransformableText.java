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

package net.impactdev.impactor.api.text.transforming;

import net.impactdev.impactor.api.Impactor;
import net.impactdev.impactor.api.text.transforming.transformers.TextTransformer;
import net.impactdev.impactor.api.utility.builders.Builder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentBuilderApplicable;
import net.kyori.adventure.text.ComponentLike;

import java.util.function.Supplier;

public interface TransformableText extends ComponentLike {

    Supplier<Component> supplier();

    TextTransformer transformer();

    static TransformableTextBuilder builder() {
        return Impactor.instance().builders().provide(TransformableTextBuilder.class);
    }

    interface TransformableTextBuilder extends Builder<TransformableText> {

        TransformableTextBuilder supplier(Supplier<Component> supplier);

        TransformableTextBuilder transformer(TextTransformer transformer);

    }

}
