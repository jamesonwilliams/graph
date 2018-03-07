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
 * Vertex is a simple value store, used to represent a node in a Graph.
 * @param <T> The type of value stored in the vertex
 */
public final class Vertex<T> {

    /**
     * The value stored in the vertex.
     */
    private final T value;

    /**
     * Constructs a new Vertex.
     * @param value The value stored in the vertex
     */
    private Vertex(final T value) {
        this.value = value;
    }

    /**
     * Creates a vertex from a value.
     * @param value The value to store in the vertex
     * @param <T> The type of the value
     * @return A vertex containing a value
     */
    public static <T> Vertex<T> create(final T value) {
        return new Vertex<T>(value);
    }

    /**
     * Gets the stored value.
     * @return The stored value
     */
    public T value() {
        return this.value;
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

