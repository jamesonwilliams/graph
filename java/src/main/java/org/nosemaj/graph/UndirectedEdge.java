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

import org.nosemaj.graph.util.Preconditions;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * UndirectedEdge is an edge defined by a simple set of two vertices.
 * There is no distinction between the two vertices, but the vertices
 * must be unique.
 */
public class UndirectedEdge implements Edge<Set<Vertex>> {

    /**
     * The set of vertices that comprise the edge.
     */
    private final Set<Vertex> vertices;

    /**
     * Constructs a new simple edge.
     * @param first The first of the two (not necessarily distinct)
     *              vertices in the edge
     * @param second The second of the two (not necessarily distinct)
     *               vertices in the edge
     */
    public UndirectedEdge(final Vertex first, final Vertex second) {
        Preconditions.notNull(first, "first must be non-null.");
        Preconditions.notNull(second, "second must be non-null.");
        Preconditions.notEqual(first, second,
            "first and second must be distinct.");

        Set<Vertex> modifiableSet = new HashSet<>(Arrays.asList(first, second));
        this.vertices = Collections.unmodifiableSet(modifiableSet);
    }

    @Override
    public final Set<Vertex> vertices() {
        return vertices;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(vertices);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object thatObject) {
        if (thatObject == null) {
            return false;
        }

        if (!getClass().equals(thatObject.getClass())) {
            return false;
        }

        return Objects.equals(vertices(),
            getClass().cast(thatObject).vertices());
    }
}
