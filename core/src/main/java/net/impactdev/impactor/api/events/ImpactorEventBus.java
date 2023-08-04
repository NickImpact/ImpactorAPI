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

package net.impactdev.impactor.api.events;

import net.impactdev.impactor.api.logging.Log4jLogger;
import net.impactdev.impactor.api.utility.printing.PrettyPrinter;
import net.kyori.event.EventBus;
import net.kyori.event.EventSubscriber;
import net.kyori.event.EventSubscription;
import net.kyori.event.PostResult;
import org.apache.logging.log4j.LogManager;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class ImpactorEventBus implements EventBus<ImpactorEvent> {

    private static final ImpactorEventBus INSTANCE = new ImpactorEventBus();

    public static ImpactorEventBus bus() {
        return INSTANCE;
    }

    private final EventBus<ImpactorEvent> delegate =  EventBus.create(ImpactorEvent.class);
    private final Log4jLogger logger = new Log4jLogger(LogManager.getLogger("Impactor (Events)"));

    private ImpactorEventBus() {}

    @Override
    public @NonNull Class<ImpactorEvent> type() {
        return ImpactorEvent.class;
    }

    @Override
    public @NonNull PostResult post(@NonNull ImpactorEvent event) {
        Supplier<PrettyPrinter> raiser = () -> {
            PrettyPrinter printer = new PrettyPrinter(80);
            printer.title("Event Invocation Results");
            printer.add("Event: " + event.getClass().getSimpleName());
            return printer;
        };

        PostResult result = this.delegate.post(event);
        if(!result.wasSuccessful()) {
            PrettyPrinter printer = raiser.get();
            printer.add("Exceptions Encountered: " + result.exceptions().size());
            printer.newline().add("Encountered exception traces will now be printed below...");
            printer.hr('-');

            AtomicInteger id = new AtomicInteger(1);
            result.exceptions().forEach((subscriber, throwable) -> {
                printer.add("%d: %s", id.getAndIncrement(), subscriber);
                printer.add(throwable, 2);
            });

            printer.log(this.logger, PrettyPrinter.Level.ERROR);
        }

        return result;
    }

    @Override
    public boolean subscribed(@NonNull Class<? extends ImpactorEvent> type) {
        return this.delegate.subscribed(type);
    }

    @Override
    public @NonNull <T extends ImpactorEvent> EventSubscription subscribe(@NonNull Class<T> event, @NonNull EventSubscriber<? super T> subscriber) {
        return this.delegate.subscribe(event, subscriber);
    }

    @Override
    public void unsubscribeIf(@NonNull Predicate<EventSubscriber<? super ImpactorEvent>> predicate) {
        this.delegate.unsubscribeIf(predicate);
    }
}
