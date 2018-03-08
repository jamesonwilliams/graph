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

package org.nosemaj.graph;

import java.util.Objects;

/**
 * A weight is a numeric comparison value for an edge.
 * Generics nonsense was taken from https://stackoverflow.com/a/7966120/695787
 * @param <T> Type for weight values
 */
public final class Weight<T extends Number & Comparable<? super T>> {

    /**
     * The weight value.
     */
    private final T value;

    /**
     * Constructs a new weight.
     * @param value The weight value
     */
    private Weight(final T value) {
        this.value = value;
    }

    /**
     * Gets the weight value.
     * @return weight value
     */
    public T value() {
        return this.value;
    }

    /**
     * Gets the weight of a given value.
     * @param value The value for which to determine a weight
     * @param <T> Type for weight values
     * @return A weight
     */
    public static <T extends Number & Comparable<? super T>> Weight<T> of(
            final T value) {

        return new Weight<T>(value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean equals(final Object thatObject) {
        if (thatObject == null) {
            return false;
        }

        if (getClass().equals(thatObject.getClass())) {
            return Objects.equals(value(),
                getClass().cast(thatObject).value());
        }

        return false;
    }
}

