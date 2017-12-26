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
import java.util.List;
import java.util.Objects;

/**
 * DirectedEdge is an edge that distinguished vertices into source and
 * target, assuming a directional relationship between them.
 * DirectedEdge does not require its vertices to be unique.
 */
public class DirectedEdge implements Edge<List<Vertex>>,
       Directed<Vertex, Vertex> {

    /**
     * The list or vertices, ordered from source to target.
     */
    private final List<Vertex> vertices;

    /**
     * Constructs a new DirectedEdge.
     * @param source The source vertex
     * @param target The target vertex
     */
    public DirectedEdge(final Vertex source, final Vertex target) {
        Preconditions.notNull(source, "source must be non-null.");
        Preconditions.notNull(target, "target must be non-null.");

        List<Vertex> modifiableList = Arrays.asList(source, target);
        this.vertices = Collections.unmodifiableList(modifiableList);
    }

    @Override
    public final List<Vertex> vertices() {
        return this.vertices;
    }

    @Override
    public final Vertex source() {
        return this.vertices.get(0);
    }

    @Override
    public final Vertex target() {
        return this.vertices.get(1);
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
