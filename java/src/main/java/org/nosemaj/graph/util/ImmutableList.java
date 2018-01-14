/*
 * Copyright 2017 nosemaj.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.nosemaj.graph.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * An immutable list is a fixed-size list, the members of which may not
 * be modified.
 *
 * Note: this is not taken from com.google.collect because as of yet
 * we're only using this one thing; we don't need the entire library as
 * a dependency.
 */
public final class ImmutableList {

    /**
     * Dis-allows instantiation of this utility class.
     */
    private ImmutableList() {
        final String message = "No instances allowed for this utility class.";
        throw new UnsupportedOperationException(message);
    }

    /**
     * Gets an immutable list of items.
     * @param items An array of items to put into the list
     * @param <T> The type of item in the list
     * @return An immutable list of the items
     */
    @SafeVarargs
    public static <T> List<T> of(final T... items) {
        return Collections.unmodifiableList(Arrays.asList(items));
    }

    /**
     * Gets an immutable list of items.
     * @param items A collection of items to go into the list
     * @param <T> The type of item in the list
     * @return An immutable list of items
     */
    public static <T> List<T> of(final Collection<T> items) {
        return Collections.unmodifiableList(new ArrayList<>(items));
    }
}
