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

package net.impactdev.impactor.api.utility.pointers;

import net.kyori.adventure.pointer.Pointer;
import net.kyori.adventure.pointer.Pointered;

import java.util.function.Supplier;

public interface PointerCapable extends Pointered {

    default <T> T require(Pointer<T> pointer) {
        return this.pointers().get(pointer).orElseThrow(() -> new IllegalArgumentException("No value registered for pointer: " + pointer.key()));
    }

    /**
     * Registers a pointered value to the context using the given pointer as its key. Existing mappings
     * using the same key will be overridden.
     *
     * <p>Unlike {@link #withDynamic(Pointer, Supplier)}, this method uses static instances. If you wish for the data
     * represented by this pointer to be dynamic, use that method instead!</p>
     *
     * @param pointer The pointer to serve as the key to locating the data
     * @param value A static instance that will otherwise remain constant.
     * @return The updated context
     * @param <T> The type represented by the pointer
     * @since 5.1.1
     */
    <T> PointerCapable with(Pointer<T> pointer, T value);

    /**
     * Registers a pointered value to the context using the given pointer as its key. Existing mappings
     * using the same key will be overridden.
     *
     * <p>Values registered in this way will always be fetched dynamically, and can differ from one instance
     * to the other based on access time.</p>
     *
     * @param pointer The pointer to serve as the key to locating the data
     * @param supplier A supplier to create the data when accessed
     * @return The updated context
     * @param <T> The type represented by the pointer
     * @since 5.1.1
     */
    <T> PointerCapable withDynamic(Pointer<T> pointer, Supplier<T> supplier);

}
