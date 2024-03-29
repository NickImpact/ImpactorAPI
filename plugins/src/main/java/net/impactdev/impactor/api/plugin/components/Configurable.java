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

package net.impactdev.impactor.api.plugin.components;

import net.impactdev.impactor.api.configuration.Config;

import java.nio.file.Path;

/**
 * A marker interface which labels a given plugin as a configurable plugin. This is meant to expand the
 * capabilities of a base plugin such that accessing configuration data is made easier.
 */
public interface Configurable {

	/**
	 * Represents the path to the configuration directory of the plugin.
	 *
	 * @return A {@link Path} representing the target configuration directory
	 */
	Path configurationDirectory();

	/**
	 * Specifies the configuration used by a plugin in the most general of terms. In projects
	 * with multiple types of configs, this should effectively map to your main configuration
	 * settings.
	 *
	 * @return The config representing controllable functions of a plugin
	 */
	Config configuration();

}
