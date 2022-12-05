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

package net.impactdev.impactor.api.platform.sources.metadata;

import net.impactdev.impactor.api.platform.sources.PlatformSource;
import net.impactdev.impactor.api.services.economy.accounts.AccountAccessor;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.intellij.lang.annotations.Pattern;
import org.intellij.lang.annotations.Subst;
import org.spongepowered.math.vector.Vector2d;
import org.spongepowered.math.vector.Vector3d;

public final class MetadataKeys {

    /**
     * Specifies the display name of a source, which may or may not differ from the name specified via
     * {@link PlatformSource#name()}
     */
    public static final MetadataKey<Component> DISPLAY_NAME = MetadataKey.create(impactor("display-name"));

    /**
     * Indicates the current world the player exists in.
     */
    public static final MetadataKey<Key> WORLD = MetadataKey.create(impactor("world"));

    /**
     * Specifies the current block coordinates of a player. This is where the player
     * would be standing in a particular world.
     */
    public static final MetadataKey<Vector3d> POSITION = MetadataKey.create(impactor("position"));

    /**
     * Specifies the rotation of a particular source. This typically applies to sources where a visible
     * entity is on the platform, such as a player or a particular entity.
     */
    public static final MetadataKey<Vector2d> ROTATION = MetadataKey.create(impactor("rotation"));

    /**
     * Provides an accessor meant to aid in retrieving accounts pertaining to the player for the economy
     * service.
     */
    public static final MetadataKey<AccountAccessor> ACCOUNT_ACCESSOR = MetadataKey.create(impactor("accounts"));

    /**
     * Used to indicate the permission level of a particular platform source. This is used typically for
     * game-specific permission interaction, and may or may not be involved on a proxy service.
     */
    public static final MetadataKey<Integer> PERMISSION_LEVEL = MetadataKey.create(impactor("permission-level"));

    private static Key impactor(@Subst("dummy") @Pattern("[a-z0-9_\\-./]+") String key) {
        return Key.key("impactor", key);
    }

}
