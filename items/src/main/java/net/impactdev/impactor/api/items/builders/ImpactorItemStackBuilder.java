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

package net.impactdev.impactor.api.items.builders;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import net.impactdev.impactor.api.items.ImpactorItemStack;
import net.impactdev.impactor.api.items.properties.MetaFlag;
import net.impactdev.impactor.api.items.properties.enchantments.Enchantment;
import net.impactdev.impactor.api.utility.builders.Builder;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.text.Component;
import org.checkerframework.checker.index.qual.Positive;
import org.jetbrains.annotations.Contract;

import java.util.Collection;

@SuppressWarnings({"UnusedReturnValue", "unused"})
public interface ImpactorItemStackBuilder<I extends ImpactorItemStack, B extends ImpactorItemStackBuilder<I, B>>
        extends Builder<I> {

    /**
     * Sets the quantity of the stack for this item.
     *
     * @param quantity The size of the item stack
     * @return This builder
     */
    @CanIgnoreReturnValue
    @Contract("_ -> this")
    B quantity(final int quantity);

    /**
     * Sets the display title of the item stack. By default, this component will appear with
     * italics due to a Minecraft standard. In order to combat this, you need to forcibly
     * set within the component's style to have italics disabled.
     *
     * @param title The title for the item stack
     * @return This builder
     */
    @CanIgnoreReturnValue
    @Contract("_ -> this")
    B title(final Component title);

    /**
     * Applies the set of lore to the item stack. By default, each line will be subject to italics
     * per a Minecraft standard. In order to combat this, you need to forcibly set within the
     * component's style to have italics disabled.
     *
     * @param lore The collection of components to assign to the stack's lore, in order of
     *             occurrence
     * @return This builder
     */
    @CanIgnoreReturnValue
    @Contract("_ -> this")
    B lore(final Component... lore);

    /**
     * Applies the set of lore to the item stack. By default, each line will be subject to italics
     * per a Minecraft standard. In order to combat this, you need to forcibly set within the
     * component's style to have italics disabled.
     *
     * @param lore The collection of components to assign to the stack's lore, in order of
     *             occurrence
     * @return This builder
     */
    @CanIgnoreReturnValue
    @Contract("_ -> this")
    B lore(final Collection<Component> lore);

    /**
     * Applies an enchantment to the resulting item stack. This enchantment expects a resource key
     * as well as a level that should apply to the stack.
     *
     * <p>If you are looking simply to apply a glowing effect to an item, consider delegating
     * to using {@link #glow}, which will apply the necessary meta flag to hide enchantments
     * for you on top of assigning an enchantment.
     *
     * @param enchantment The enchantment to apply to the item stack
     * @return This builder
     */
    @CanIgnoreReturnValue
    @Contract("_ -> this")
    B enchantment(final Enchantment enchantment);

    @CanIgnoreReturnValue
    @SuppressWarnings("unchecked")
    @Contract("_,_ -> this")
    default B enchantments(final Enchantment primary, final Enchantment... enchantments) {
        this.enchantment(primary);
        for(Enchantment enchantment : enchantments) {
            this.enchantment(enchantment);
        }

        return (B) this;
    }

    @CanIgnoreReturnValue
    @SuppressWarnings("unchecked")
    @Contract("_ -> this")
    default B enchantments(final Collection<Enchantment> enchantments) {
        for(Enchantment enchantment : enchantments) {
            this.enchantment(enchantment);
        }

        return (B) this;
    }

    /**
     * Specifies the durability of an item. In the case of tools, this is the amount of uses
     * remaining before the tool would break.
     *
     * <p>The given value meets two preconditions, which are non-fatal. If the specified durability
     * is lower than 1 or the max durability of an item, this builder will resolve to the respective
     * min/max accordingly.
     *
     * @param durability The amount of durability remaining on the target item.
     * @return This builder
     */
    @CanIgnoreReturnValue
    @Contract("_ -> this")
    B durability(@Positive final int durability);

    /**
     * Marks the resulting item stack as unbreakable. This simply means that any tool that is
     * expected to take damage and eventually break over time from usage should not receive
     * any actual damage, and simply have infinite durability.
     *
     * @return This builder
     */
    @CanIgnoreReturnValue
    @Contract("-> this")
    default B unbreakable() {
        return this.unbreakable(true);
    }

    /**
     * Marks the resulting item stack with the unbreaking capability if given a true state.
     * See {@link #unbreakable()} for additional details on an item being unbreakable.
     *
     * @return This builder
     * @see #unbreakable()
     */
    @CanIgnoreReturnValue
    @Contract("_ -> this")
    B unbreakable(boolean state);

    /**
     * Applies a flag to the item stack which is capable of hiding specific data points that would
     * normally insert themselves into the viewable data components. For instance, when you enchant
     * an item, the enchants are normally listed outside the lore. You can hide enchantments
     * from appearing using the {@link MetaFlag#ENCHANTMENTS} key.
     *
     * @param flags A set of flags to apply
     * @return This builder
     */
    @CanIgnoreReturnValue
    @Contract("_ -> this")
    B hide(final MetaFlag... flags);

    @CanIgnoreReturnValue
    @Contract("_ -> this")
    B hide(final Collection<MetaFlag> flags);

    /**
     * Applies the unbreaking enchantment as well as the {@link MetaFlag#ENCHANTMENTS} flag to
     * the item stack to give it a glowing effect (the enchanted stack effect). This will avoid
     * showing the enchantments on the actual displayable information, but maintain the glowing
     * effect on the stack.
     *
     * @return This builder
     */
    @CanIgnoreReturnValue
    @Contract("-> this")
    B glow();

    @CanIgnoreReturnValue
    @Contract("_ -> this")
    B customModelData(final int value);

    /**
     * Applies an additional set of NBT to the resulting item. This field is primarily useful
     * in situations where resolution type of an item configuration might be unclear, allowing
     * for raw NBT to aid in replacing the need for multiple different deserializers.
     *
     * @return This builder
     */
    @CanIgnoreReturnValue
    @Contract("_ -> this")
    B nbt(final CompoundBinaryTag nbt);

}
