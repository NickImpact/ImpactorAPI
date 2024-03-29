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

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.impactdev.impactor.api.items.ImpactorItemStack;
import net.impactdev.impactor.api.items.properties.MetaFlag;
import net.impactdev.impactor.api.items.properties.enchantments.Enchantment;
import net.impactdev.impactor.api.items.properties.enchantments.Enchantments;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unchecked")
public abstract class AbstractStackBuilder<I extends ImpactorItemStack, B extends ImpactorItemStackBuilder<I, B>> implements ImpactorItemStackBuilder<I, B> {

    public Component title;
    public List<Component> lore = Lists.newArrayList();
    public int quantity = 1;
    public Set<Enchantment> enchantments = Sets.newLinkedHashSet();
    public Set<MetaFlag> flags = Sets.newHashSet();
    public int durability;
    public boolean unbreakable;

    @Nullable
    public Integer customModelData = null;
    public CompoundBinaryTag nbt;

    @Override
    public B quantity(int quantity) {
        this.quantity = quantity;
        return (B) this;
    }

    @Override
    public B title(final Component title) {
        this.title = title;
        return (B) this;
    }

    @Override
    public B lore(Component... lore) {
        this.lore.addAll(Arrays.asList(lore));
        return (B) this;
    }

    @Override
    public B lore(Collection<Component> lore) {
        this.lore.addAll(lore);
        return (B) this;
    }

    @Override
    public B enchantment(Enchantment enchantment) {
        this.enchantments.add(enchantment);
        return (B) this;
    }

    @Override
    public B durability(int durability) {
        this.durability = durability;
        return (B) this;
    }

    @Override
    public B unbreakable(boolean state) {
        this.unbreakable = state;
        return (B) this;
    }

    @Override
    public B hide(MetaFlag... flags) {
        this.flags.addAll(Arrays.asList(flags));
        return (B) this;
    }

    @Override
    public B hide(Collection<MetaFlag> flags) {
        this.flags.addAll(flags);
        return (B) this;
    }

    @Override
    public B glow() {
        this.enchantment(Enchantment.create(Enchantments.UNBREAKING, 1));
        this.hide(MetaFlag.ENCHANTMENTS);
        return (B) this;
    }

    @Override
    public B customModelData(int value) {
        this.customModelData = value;
        return (B) this;
    }

    @Override
    public B nbt(CompoundBinaryTag nbt) {
        this.nbt = nbt;
        return (B) this;
    }
}
