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

package net.impactdev.impactor.api.test;

import net.impactdev.impactor.api.events.ImpactorEvent;
import net.impactdev.impactor.api.events.ImpactorEventBus;
import net.kyori.event.EventBus;
import net.kyori.event.EventSubscription;
import net.kyori.event.PostResult;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class EventTests {

    @Test
    public void validate() {
        EventBus<ImpactorEvent> bus = ImpactorEventBus.bus();
        TestEvent event = new TestEvent();

        AtomicBoolean state = new AtomicBoolean();
        PostResult result = bus.post(event);

        assertTrue(result.wasSuccessful());
        assertFalse(state.get());

        EventSubscription subscription = bus.subscribe(TestEvent.class, e -> state.set(true));
        result = bus.post(event);
        assertTrue(result.wasSuccessful());
        assertTrue(state.get());
        assertTrue(bus.subscribed(TestEvent.class));

        subscription.unsubscribe();
        assertFalse(bus.subscribed(TestEvent.class));

        bus.subscribe(TestEvent.class, e -> {
            throw new Exception("Expected Test Exception");
        });
        result = bus.post(event);
        assertFalse(result.wasSuccessful());
        assertNotEquals(0, result.exceptions().size());
    }

    public static final class TestEvent implements ImpactorEvent {}

}
