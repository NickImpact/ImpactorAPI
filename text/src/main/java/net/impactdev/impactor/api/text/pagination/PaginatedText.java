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

package net.impactdev.impactor.api.text.pagination;

import net.impactdev.impactor.api.Impactor;
import net.impactdev.impactor.api.utility.builders.Builder;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Represents a chat component which wraps a set of other chat components.
 * Rather than be displayed all at once, a Pagination separates the contents into
 * multiple pages to be displayed separately. Paginations are meant to be controlled
 * via the Minecraft chat window through clickable components, but can additionally
 * be manipulated through commands, where the pagination ID is known.
 *
 * <p>
 * <h2>Licensing</h2>
 * This file is heavily inspired from SpongeAPI, and as such, adheres to its licensing
 * agreement of MIT.
 */
public interface PaginatedText {

    /**
     * Represents a title attribute for the pagination. The title, if set, will appear
     * centered on the first line of a pagination, surrounded by the padding component.
     * Otherwise, the padding characters will occupy the entirety of the first line when
     * not set.
     *
     * @return A component representing the title of this pagination, if set
     */
    Optional<Component> title();

    /**
     * Denotes the list of components that will fill the pagination. These components
     * will be separated onto multiple pages should they exceed the max lines configured
     * for the pagination.
     *
     * @return The list of components to display
     */
    List<Component> contents();

    /**
     * Specifies a header line that would display on the pagination BEFORE the contents
     * of EVERY page.
     *
     * @return A component representing the header of each paginated page, if set
     */
    Optional<Component> header();

    /**
     * Specifies a header line that would display on the pagination AFTER the contents
     * of EVERY page.
     *
     * @return A component representing the footer of each paginated page, if set
     */
    Optional<Component> footer();

    /**
     * Represents the component that will be used to pad components, such at the title and
     * next page indicators.
     *
     * @return A component used to pad additional elements
     */
    Component padding();

    /**
     * Specifies the max number of lines allowed at once on a paginated page. This number
     * includes the header, footer, title, and next/previous page index lines.
     *
     * @return The number of lines that can appear at once on a paginated page.
     */
    int lines();

    /**
     * Sends this pagination to the target audience, opening at the first page.
     *
     * @param audience The audience which should receive this pagination
     */
    default void send(final @NotNull Audience audience) {
        this.send(audience, 1);
    }

    /**
     * Sends this pagination to the target audience, viewing the specific page specified.
     *
     * @param audience The audience which should receive this pagination
     * @param page The page to view
     */
    void send(final @NotNull Audience audience, final int page);

    static PaginatedTextBuilder builder() {
        return Impactor.instance().builders().provide(PaginatedTextBuilder.class);
    }

    interface PaginatedTextBuilder extends Builder<PaginatedText> {

        PaginatedTextBuilder title(Component title);

        PaginatedTextBuilder contents(Component... contents);

        PaginatedTextBuilder contents(Collection<Component> contents);

        PaginatedTextBuilder header(Component header);

        PaginatedTextBuilder footer(Component footer);

        PaginatedTextBuilder padding(Component padding);

        PaginatedTextBuilder lines(int lines);

    }

}
