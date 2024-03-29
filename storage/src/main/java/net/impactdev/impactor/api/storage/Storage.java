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

package net.impactdev.impactor.api.storage;

import net.impactdev.impactor.api.utility.printing.PrettyPrinter;
import java.util.concurrent.CompletableFuture;

public interface Storage {

	/**
	 * Initializes the Storage Provider
	 *
	 * @throws Exception In the event any error manages to occur during initialization
	 */
	void init() throws Exception;

	/**
	 * Closes the Storage provider. This is where we should perform our final operations before
	 * we kill the system.
	 *
	 * @throws Exception In the event any error manages to occur during shutdown
	 */
	void shutdown() throws Exception;

	/**
	 * Provides metadata information regarding the storage provider in use. The given printer
	 * will be setup for additional information at time of request, so special formatting is
	 * undesired. This method should simply fill the printer with basic information as deemed
	 * fit.
	 *
	 * @param printer The printer that'll be used to write the metadata of the storage provider
	 * @return A future with no return value, simply indicating the result of the print
	 * operation as it completes.
	 */
	CompletableFuture<Void> meta(PrettyPrinter printer);
}
